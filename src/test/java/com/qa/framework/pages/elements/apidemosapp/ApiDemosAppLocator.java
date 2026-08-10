package com.qa.framework.pages.elements.apidemosapp;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;

import java.time.Duration;

public class ApiDemosAppLocator {
        public ApiDemosAppLocator(WebDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @AndroidFindBy(xpath = "//*[@content-desc='App']")
    public WebElement buttonMenuApp;

    @AndroidFindBy(xpath = "//*[@content-desc='Activity']")
    public WebElement buttonMenuActivity;

    @AndroidFindBy(xpath = "//*[@content-desc='Hello World']")
    public WebElement buttonMenuHW;

    @AndroidFindBy(xpath = "//*[contains(@resource-id, 'id/text')]")
    public WebElement textHW;
}
