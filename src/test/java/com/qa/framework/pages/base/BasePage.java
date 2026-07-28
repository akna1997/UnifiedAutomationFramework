package com.qa.framework.pages.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import com.qa.framework.utils.ConfigReader;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    String platform = com.qa.framework.utils.ConfigReader.getProperty("platform").toLowerCase();
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    public BasePage(WebDriver driver) {
        long waitSeconds = Long.parseLong(ConfigReader.getProperty("timeout"));
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(waitSeconds));
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    protected void writeText(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected boolean isDisplayed(WebElement element) {
        try {
            return wait.until(ExpectedConditions.visibilityOf(element)).isDisplayed();
        } catch (Exception e) {
            return false;
        }
    }

    protected void pressBack() {
        log.info("Press physical back button on {} ...", platform);

        if (platform.equals("android")) {
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
        } else {
            driver.navigate().back();
        }
    }

    protected void pressEnter(WebElement element) {
        log.info("Press physical Enter button on {} ...", platform);
        
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