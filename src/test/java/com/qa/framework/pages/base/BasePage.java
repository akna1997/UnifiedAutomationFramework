package com.qa.framework.pages.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qa.framework.utils.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Objects;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    String platform = com.qa.framework.utils.ConfigReader.getProperty("platform").toLowerCase();
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    // Konstanta retry & tunggu untuk interaksi yang flaky
    private static final int DEFAULT_RETRIES = 2;
    private static final Duration RETRY_PAUSE = Duration.ofMillis(300);

    public BasePage(WebDriver driver) {
        long waitSeconds = Long.parseLong(ConfigReader.getProperty("timeout"));
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
    }

    /**
     * Functional interface untuk aksi yang berpotensi flaky (stale / intercept / not-interactable).
     */
    @FunctionalInterface
    private interface ElementAction {
        void perform() throws Exception;
    }

    /**
     * Retry aman untuk aksi elemen yang umum flaky di Selenium/Appium.
     * Mengulang saat terjadi StaleElementReferenceException, ElementClickInterceptedException,
     * atau ElementNotInteractableException, lalu melempar kembali exception asli bila habis.
     */
    private void safeRetry(String actionName, WebElement element, ElementAction action) {
        int attempt = 0;
        WebDriverException last = null;
        while (attempt <= DEFAULT_RETRIES) {
            try {
                action.perform();
                if (attempt > 0) {
                    log.info("{} berhasil setelah {}x retry pada {}", actionName, attempt, describe(element));
                }
                return;
            } catch (WebDriverException e) {
                // Hanya retry exception yang dikenal flaky; exception lain dilempar langsung
                if (!isRetryable(e)) {
                    throw e;
                }
                last = e;
                if (attempt >= DEFAULT_RETRIES) {
                    break;
                }
                attempt++;
                log.warn("{} gagal ({}), retry ke-{} pada {}", actionName, e.getClass().getSimpleName(), attempt, describe(element));
                try {
                    Thread.sleep(RETRY_PAUSE.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } catch (Exception e) {
                // Exception non-WebDriver (mis. RuntimeException dari lambda) langsung dilempar
                throw new RuntimeException("Gagal menjalankan " + actionName, e);
            }
        }
        // Lempar kembali exception asli agar pemanggil (mis. click) bisa fallback
        throw last;
    }

    private boolean isRetryable(WebDriverException e) {
        return e instanceof StaleElementReferenceException
                || e instanceof ElementClickInterceptedException
                || e instanceof ElementNotInteractableException;
    }

    private void scrollIntoView(WebElement element) {
        if (platform.equals("android")) {
            // Android: biarkan UiAutomator yang menemukan elemen, tidak pakai JS scroll
            return;
        }
        try {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        } catch (Exception ignored) {
            log.debug("scrollIntoView dilewati untuk {}", describe(element));
        }
    }

    private String describe(WebElement element) {
        try {
            return element.toString();
        } catch (Exception e) {
            return "unknown-element";
        }
    }

    protected void click(WebElement element) {
        Objects.requireNonNull(element, "Element tidak boleh null saat click()");
        log.info("Klik elemen: {}", describe(element));

        scrollIntoView(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            safeRetry("click", element, element::click);
        } catch (ElementClickInterceptedException e) {
            log.warn("Elemen ter-intercept, fallback JS click: {}", e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void writeText(WebElement element, String text) {
        Objects.requireNonNull(element, "Element tidak boleh null saat writeText()");
        log.info("Isi teks ke elemen: {}", describe(element));

        wait.until(ExpectedConditions.visibilityOf(element));
        safeRetry("writeText", element, () -> {
            element.clear();
            element.sendKeys(text);
        });
    }

    protected boolean isDisplayed(WebElement element) {
        if (element == null) {
            log.debug("isDisplayed: element null -> false");
            return false;
        }
        try {
            boolean visible = wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
            log.debug("isDisplayed: {} -> {}", describe(element), visible);
            return visible;
        } catch (Exception e) {
            log.debug("isDisplayed: {} tidak terlihat -> false ({})", describe(element), e.getClass().getSimpleName());
            return false;
        }
    }

    protected void pressBack() {
        log.info("Tekan tombol back pada platform {} ...", platform);

        if (platform.equals("android")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
        } else {
            driver.navigate().back();
        }
    }

    protected void pressEnter(WebElement element) {
        Objects.requireNonNull(element, "Element tidak boleh null saat pressEnter()");
        log.info("Tekan tombol Enter pada platform {} ...", platform);

        if (platform.equals("android")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else {
            element.sendKeys(Keys.ENTER);
        }
    }

//    protected void executeSearch() {
//        if (platform.equals("android")) {
//            System.out.println("Memaksa Android melakukan IME Action SEARCH...");
//
//            Map<String, Object> actionArgs = new HashMap<>();
//            actionArgs.put("action", "search");
//
//            ((AndroidDriver) driver).executeScript("mobile: performEditorAction", actionArgs);
//        }
//    }
}
