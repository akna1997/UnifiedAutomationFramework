package com.qa.framework.pages.base;

import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected WebDriverWait wait;
    String platform = com.qa.framework.utils.ConfigReader.getProperty("platform").toLowerCase();

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
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
        if (platform.equals("android")) {
            System.out.println("Menekan tombol fisik BACK di Android...");
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.BACK));
        } else {
            System.out.println("Menekan tombol BACK di Browser Web...");
            driver.navigate().back();
        }
    }

    protected void pressEnter(WebElement element) {
        if (platform.equals("android")) {
            System.out.println("Menekan tombol ENTER di Android...");
            ((AndroidDriver) driver).pressKey(new KeyEvent(AndroidKey.ENTER));
        } else {
            System.out.println("Menekan tombol ENTER di Browser Web...");
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