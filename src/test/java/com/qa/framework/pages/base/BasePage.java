package com.qa.framework.pages.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

public class BasePage extends UtilBasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final Logger log = LoggerFactory.getLogger(BasePage.class);
    String platform = com.qa.framework.utils.ConfigReader.getProperty("platform").toLowerCase();

    public BasePage(WebDriver driver) {
        long waitSeconds = Long.parseLong(ConfigReader.getProperty("timeout"));
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
    }

    private void scrollIntoView(WebElement element) {
        if (platform.equals("android")) {
            // nanti tambahin func buat scroll to view android
            return;
        }
        try {
            ((JavascriptExecutor) driver)
            .executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        } catch (Exception ignored) {
            log.debug("scrollIntoView for {}", describe(element));
        }
    }

    protected void click(WebElement element) {
        Objects.requireNonNull(element, "Element cannot be null while click()");
        log.info("Klik elemen: {}", describe(element));

        scrollIntoView(element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            safeRetry("click", element, element::click);
        } catch (ElementClickInterceptedException e) {
            log.warn("Element intercepted, fallback JS click: {}", e.getMessage());
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }

    protected void writeText(WebElement element, String text) {
        Objects.requireNonNull(element, "Element cannot be null while writeText()");
        log.info("Fill teks to element: {}", describe(element));

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
            log.debug("isDisplayed: {} not appear -> false ({})", describe(element), e.getClass().getSimpleName());
            return false;
        }
    }

    protected void pressBack() {
        log.info("Press back button on platform {} ...", platform);

        if (platform.equals("android")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
        } else {
            driver.navigate().back();
        }
    }

    protected void pressEnter(WebElement element) {
        Objects.requireNonNull(element, "Element cannot be null while pressEnter()");
        log.info("Press enter button on platform {} ...", platform);

        if (platform.equals("android")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else {
            element.sendKeys(Keys.ENTER);
        }
    }

    protected String getAttribute(WebElement element, String attribute) {
        String[] attrValueWrapper = new String[]{"initial"};
        Objects.requireNonNull(element, "Element cannot be null while getting its attribute");
        log.info("Get attribute {} from element: {}", attribute, describe(element));


        wait.until(ExpectedConditions.visibilityOf(element));
        safeRetry("getAttribute", element, () -> {
            attrValueWrapper[0] = element.getAttribute(attribute).toString();
        });

        return attrValueWrapper[0];
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
