# 🚀 Unified Automation Framework (Web, Mobile, & API)

![Platform](https://img.shields.io/badge/Platform-Windows%20%7C%20macOS%20%7C%20Linux-blue)
![Java](https://img.shields.io/badge/Java-17-ED8B00?logo=openjdk&logoColor=white)
![Maven](https://img.shields.io/badge/Maven-Build-C71A22?logo=apachemaven&logoColor=white)
![Selenium](https://img.shields.io/badge/Selenium-Web-43B02A?logo=selenium&logoColor=white)
![Appium](https://img.shields.io/badge/Appium-Mobile-662F8F?logo=appium&logoColor=white)
![Cucumber](https://img.shields.io/badge/Cucumber-BDD-23D96C?logo=cucumber&logoColor=white)

Selamat datang di **Unified Automation Framework**! Ini adalah framework otomatisasi pengujian berskala *Enterprise* yang dirancang untuk menguji aplikasi **Web, Mobile (Android/iOS), dan API** dalam satu repositori yang terintegrasi.

Framework ini dirakit menggunakan perpaduan teknologi berstandar industri modern untuk memastikan stabilitas, skalabilitas, dan kemudahan pemeliharaan:

* **Bahasa Pemrograman:** Java 17
* **Depedency Manager & Build Tool:** Apache Maven
* **Web Automation Engine:** Selenium WebDriver
* **Mobile Automation Engine:** Appium
* **API Automation:** Rest-Assured
* **Testing Approach (BDD):** Cucumber (Gherkin Syntax)
* **Report Visual (Dashboard):** Allure Report
* **Continuous Integration (CI/CD):** GitHub Actions
* **Cloud Hosting Report:** GitHub Pages

---

## 🏗️ DAFTAR ISI
1. [Prasyarat Awal (Instalasi Software)](#-langkah-1-persiapan-awal)
2. [Setup Environment Variables](#-langkah-2-pengaturan-environment-variables)
3. [Tips Mobile: Cara Mencari appPackage dan appActivity](#-langkah-3-tips-mobile-cara-mencari-apppackage-dan-appactivity)
4. [Clone & Membuka Project di IntelliJ](#-langkah-4-mengunduh-dan-membuka-project)
5. [Cara Menjalankan Pengujian (Local)](#-langkah-5-local-run)
6. [Melihat Laporan Hasil Tes (Allure Report)](#-langkah-6-melihat-laporan-hasil-tes)

---

## 🛠️ LANGKAH 1: PERSIAPAN AWAL

Sebelum menjalankan framework, kamu wajib menginstal beberapa perangkat lunak berikut sesuai dengan sistem operasi laptopmu (Windows atau Mac).

### 1. Instal Git (Perekam Jejak Kode)
* **Windows:** Unduh [Git untuk Windows](https://git-scm.com/download/win). Jalankan file `.exe`, klik *Next* sampai selesai.
* **Mac:** Buka Terminal, ketik perintah `git --version`. Jika belum terpasang, Mac akan otomatis memunculkan pop-up untuk menginstal *Command Line Developer Tools*. Klik *Install*.

### 2. Instal Java JDK 17 (Bahasa Pemrograman)
Framework ini menggunakan Java versi 17.
* **Windows & Mac:** Unduh installer JDK 17 dari [Eclipse Temurin (Adoptium)](https://adoptium.net/temurin/releases/?version=17). Pilih sesuai OS (untuk Mac, sesuaikan apakah menggunakan chip Intel atau Apple Silicon/M1/M2/M3). Jalankan installer hingga selesai.

### 3. Instal Apache Maven (Pengatur Dependensi)
* **Mac (Paling Mudah via Homebrew):**
    1. Buka Terminal, instal Homebrew terlebih dahulu jika belum punya: `/bin/bash -c "$(curl -fsSL https://raw.githubusercontent.com/Homebrew/install/HEAD/install.sh)"`
    2. Ketik: `brew install maven`
* **Windows:**
    1. Unduh file zip "Binary zip archive" di [Situs Resmi Maven](https://maven.apache.org/download.cgi).
    2. Ekstrak file zip tersebut ke folder aman, misalnya di `C:\Program Files\maven\`.

### 4. Instal IntelliJ IDEA (Aplikasi Editor Kode)
* Unduh **IntelliJ IDEA Community Edition** (Gratis) dari [JetBrains](https://www.jetbrains.com/idea/download/). Jalankan installer dan ikuti petunjuknya sampai selesai.

---

## ⚙️ LANGKAH 2: PENGATURAN ENVIRONMENT VARIABLES

Langkah ini wajib agar laptopmu mengenali perintah `java` dan `mvn` dari terminal mana pun.

### 💻 Untuk Pengguna Windows:
1. Cari **"Environment Variables"** di menu pencarian Windows Start.
2. Di jendela *System Properties*, klik tombol **Environment Variables...**.
3. Di bawah **System Variables**, klik **New...** untuk membuat variabel baru:
    * **Variable name:** `JAVA_HOME`
    * **Variable value:** *Alamat folder instalasi JDK kamu* (Contoh: `C:\Program Files\Eclipse Adoptium\jdk-17.x.x-hotspot\`)
4. Buat satu lagi untuk Maven:
    * **Variable name:** `MAVEN_HOME`
    * **Variable value:** *Alamat folder ekstrak Maven kamu* (Contoh: `C:\Program Files\maven\apache-maven-3.x.x`)
5. Cari variabel bernama **`Path`** di dalam System Variables, klik **Edit...**.
6. Klik **New**, lalu tambahkan dua baris ini:
    * `%JAVA_HOME%\bin`
    * `%MAVEN_HOME%\bin`
7. Klik **OK** di semua jendela untuk menyimpan.

### 🍏 Untuk Pengguna Mac:
1. Buka Terminal, buka file profil konfigurasi (biasanya menggunakan Zsh): `nano ~/.zshrc`
2. Tambahkan baris-baris berikut di paling bawah file:
   ```bash
   export JAVA_HOME=$(/usr/libexec/java_home -v 17)
   export MAVEN_HOME=/opt/homebrew/Cellar/maven/3.x.x # Sesuaikan dengan folder install brew kamu
   export PATH=$PATH:$JAVA_HOME/bin:$MAVEN_HOME/bin
3. Simpan dengan menekan Ctrl+O, lalu Enter, kemudian keluar dengan Ctrl+X.
4. Jalankan perintah: source ~/.zshrc untuk mengaktifkan perubahan.

## 📱 LANGKAH 3: TIPS MOBILE: CARA MENCARI APPPACKAGE DAN APPACTIVITY
Saat menguji aplikasi Android via Appium, kamu wajib mendaftarkan identitas aplikasi berupa appPackage (nama ID aplikasi) dan appActivity (halaman gerbang awal aplikasi dibuka). Berikut adalah cara mencarinya:

1. Menggunakan Perintah Terminal (Paling Sering Digunakan QA Engineer)
    Hubungkan HP Android asli kamu ke laptop menggunakan kabel USB (Pastikan USB Debugging di menu Developer Options HP sudah aktif).
    
    Buka aplikasi yang ingin kamu uji di HP kamu (Misal: Aplikasi Tokopedia atau Shopee), dan biarkan aplikasi tersebut tetap terbuka di layar depan.
    
    Buka Terminal / Command Prompt di laptop, lalu jalankan perintah sakti ini:
    
    Untuk Pengguna Mac / Linux:

        adb shell dumpsys activity activities | grep mResumedActivity
 
    Untuk Pengguna Windows:

        adb shell dumpsys activity activities | findstr mResumedActivity

    Hasil yang Muncul: Terminal akan memunculkan sebaris teks panjang. Perhatikan bagian yang menyerupai pola berikut (hanya contoh):

        mResumedActivity: ActivityRecord{... u0 com.tokopedia.tkpd/com.tokopedia.home.HomeActivity t12}

    appPackage Anda adalah teks sebelum tanda garis miring (/), yaitu: com.tokopedia.tkpd
    
    appActivity Anda adalah teks setelah tanda garis miring (/), yaitu: com.tokopedia.home.HomeActivity

   *PS : kita juga butuh DeviceName, PlatformName, dan automation name, dan akan jadi seperti ini (berguna untuk appium inspector) :

   ```
   {
      "appium:deviceName": "Pixel 5",
      "appium:automationName": "UiAutomator2",
      "platformName": "Android",
      "appium:appPackage": "com.tokopedia.tkpd",
      "appium:appActivity": "com.tokopedia.home.HomeActivity"
   }

2. Menggunakan Aplikasi Pihak Ketiga (Tanpa Ketik Kode)

    Jika kamu kesulitan menggunakan Terminal, kamu bisa mendeteksi langsung lewat HP Android:

    Buka Google Play Store di HP Android kamu.

    Cari dan instal aplikasi gratis bernama `Current Activity` atau `Package Name Viewer`.

    Buka aplikasi target (misal Tokopedia), lalu buka aplikasi Current Activity tersebut melalui panel notifikasi atau pop-up floating window.

    Aplikasi akan langsung memunculkan informasi nama Package dan Current Activity yang sedang aktif di layar secara instan. Salin nilai tersebut ke dalam kode DriverManager.java kamu.

## 📥 LANGKAH 4: MENGUNDUH DAN MEMBUKA PROJECT
1. Buka Terminal (Mac) atau Command Prompt/Git Bash (Windows).

2. Masuk ke folder tempat kamu ingin menyimpan proyek, lalu klon repositori dari GitHub:

    ```Bash
    git clone [https://github.com/akna1997/UnifiedAutomationFramework.git](https://github.com/akna1997/UnifiedAutomationFramework.git)
3. Buka aplikasi IntelliJ IDEA.

4. Pilih Open, lalu arahkan ke folder UnifiedAutomationFramework yang baru saja diunduh.

5. Saat proyek terbuka, IntelliJ akan otomatis mendeteksi file pom.xml dan mengunduh semua library yang dibutuhkan di latar belakang. Tunggu hingga proses indexing di pojok kanan bawah selesai.

## 🚀 LANGKAH 5: LOCAL RUN
1. Mode Normal (Membuka Jendela Browser Chrome secara Visual)
   ```Bash
   mvn clean test -Dplatform="Web" -Dcucumber.filter.tags="@Web" -Dheadless="false"
   
2. Mode Headless (Berjalan di Latar Belakang tanpa Membuka UI Browser)

   ```Bash
   mvn clean test -Dplatform="Web" -Dcucumber.filter.tags="@Web" -Dheadless="true"
💡 Catatan Parameter:

-Dplatform: Menentukan platform pengujian (Web, Mobile, atau Api).

-Dcucumber.filter.tags: Menjalankan skenario spesifik yang memiliki tag tersebut di file .feature.

-Dheadless: Mengatur apakah browser ingin ditampilkan (false) atau disembunyikan (true).

 Contoh untuk platform lain :

      mvn clean test -Dplatform="Android" -Dcucumber.filter.tags="@Mobile"
      mvn clean test -Dplatform="iOS" -Dcucumber.filter.tags="@Mobile"
      mvn clean test -Dcucumber.filter.tags="@Api"

## 📊 LANGKAH 6: MELIHAT LAPORAN HASIL TES

Setiap kali pengujian selesai dijalankan, framework otomatis menangkap screenshot jika ada langkah yang gagal (failed). Kamu bisa melihat laporannya dengan dua cara:

1. Live Server (Sangat direkomendasikan untuk lokal)
Ketik perintah ini di terminal IntelliJ:

   ```Bash
   mvn allure:serve
   
Sistem akan otomatis merakit data dan membuka browser bawaan laptopmu untuk menampilkan dashboard interaktif Allure. Tekan Ctrl+C di terminal jika ingin mematikan server laporan.

2. Laporan Offline Statis (Untuk dikirim ke tim via ZIP)Jalankan perintah:

   ```Bash
   mvn allure:report
   
Hasil laporan statis akan terbentuk di folder: target/site/allure-maven-plugin/.