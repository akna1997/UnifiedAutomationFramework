Changelog
[1.1.0] - 2026-08-05
Added
- BasePage hardened: safeRetry (StaleElement/ElementClickIntercepted/ElementNotInteractable), scrollIntoView, null-guard, getAttribute, SLF4J logging.
- ApiClient service layer + TestContext (picocontainer DI).
Fixed
- AISteps SkipException logic terbalik (null -> skip, not fail).
- GeminiAIUtil key-empty guard.
- CI exclude @ai-test dari gate.