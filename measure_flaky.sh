#!/usr/bin/env bash
# measure_flaky.sh — ukur flaky rate suite UAF secara objektif.
# Jalankan suite N kali, parse Allure results, hitung test yang
# pernah PASSED dan pernah FAILED/BROKEN di run berbeda (= flaky).
#
# Cara pakai (di mesin yang punya browser/WebDriver):
#   ./measure_flaky.sh              # default 5 run
#   ./measure_flaky.sh 10           # 10 run
#   RUN_TAGS="not @ai-test" ./measure_flaky.sh 5
#
# Catatan: @ai-test di-exclude biar flaky rate gak tercemar ketidak-
# deterministikan AI judge (non-deterministik + butuh kredit).

set -u

RUNS="${1:-5}"
TAGS="${RUN_TAGS:-not @ai-test}"
PLATFORM="${PLATFORM:-Web}"
HEADLESS="${HEADLESS:-true}"
OUT="FLAKY_BASELINE_REPORT.md"

if ! command -v jq >/dev/null 2>&1; then
  echo "ERROR: jq diperlukan (apt install jq / brew install jq)" >&2
  exit 1
fi

# hasil per test: asosiasi name -> "passed failed ..." (status tiap run)
declare -A STATUS_BAG

run_once() {
  local run_no="$1"
  echo ">>> RUN $run_no/$RUNS  (mvn test -Dplatform=$PLATFORM -Dcucumber.filter.tags=\"$TAGS\")"
  mvn -q test -Dplatform="$PLATFORM" -Dheadless="$HEADLESS" \
    >/dev/null 2>&1 || true   # jangan exit on failure, kita hitung manual

  shopt -s nullglob
  local f name status
  for f in target/allure-results/*-result.json; do
    name=$(jq -r '.name // .fullName // "unknown"' "$f" 2>/dev/null)
    status=$(jq -r '.status // "unknown"' "$f" 2>/dev/null)
    [ -z "$name" ] && continue
    STATUS_BAG["$name"]+=" $status"
  done
  shopt -u nullglob
  # bersihkan results biar run berikutnya gak akumulasi file lama
  rm -rf target/allure-results
  mkdir -p target/allure-results
}

for ((i=1; i<=RUNS; i++)); do
  run_once "$i"
done

# hitung
total=0
flaky=0
flaky_list=()
for name in "${!STATUS_BAG[@]}"; do
  total=$((total+1))
  saw_pass=false
  saw_fail=false
  for s in ${STATUS_BAG[$name]}; do
    case "$s" in
      passed) saw_pass=true ;;
      failed|broken) saw_fail=true ;;
    esac
  done
  if $saw_pass && $saw_fail; then
    flaky=$((flaky+1))
    flaky_list+=("$name")
  fi
done

rate=0
[ "$total" -gt 0 ] && rate=$((flaky*100/total))

{
  echo "# Flaky Baseline Report"
  echo ""
  echo "- Date: $(date +%Y-%m-%d)"
  echo "- Runs: $RUNS"
  echo "- Tag filter: $TAGS"
  echo "- Total unique tests: $total"
  echo "- Flaky tests: $flaky"
  echo "- **Flaky rate: ${rate}%**"
  echo ""
  if [ "$flaky" -gt 0 ]; then
    echo "## Flaky tests"
    for t in "${flaky_list[@]}"; do echo "- $t"; done
  else
    echo "Tidak ada test flaky terdeteksi. 🎉"
  fi
} > "$OUT"

echo ""
echo "=========================================="
echo " Flaky rate: ${rate}%  ($flaky / $total tests, $RUNS runs)"
echo " Report: $OUT"
echo "=========================================="

# Exit non-zero kalau flaky rate di atas ambang (buat dipakai di CI gate opsional)
[ "$rate" -le 5 ] && exit 0 || exit 2
