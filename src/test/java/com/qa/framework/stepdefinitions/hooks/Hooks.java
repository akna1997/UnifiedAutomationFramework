package com.qa.framework.stepdefinitions.hooks;

import com.qa.framework.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import com.qa.framework.utils.SoftAssertManager;

public class Hooks {

    @Before("@Web")
    public void setupWeb(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("START SCENARIO (WEB): " + scenario.getName());
        System.out.println("Opening Browser ...");
        System.out.println("------------------------------------------------------------");

        DriverManager.initializeDriver(System.getProperty("platform"));
    }

    @Before("@Mobile")
    public void setUpMobile(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("START SCENARIO (MOBILE): " + scenario.getName());
        System.out.println("Connecting to Appium Server");
        System.out.println("------------------------------------------------------------");

        DriverManager.initializeDriver(System.getProperty("platform"));
    }

    @After("@Web")
    public void tearDownWeb(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("END SCENARIO (WEB): " + scenario.getStatus());

        TakeScreenshot(scenario);

        System.out.println("Closing Web Browser...");
        DriverManager.quitDriver();
        System.out.println("------------------------------------------------------------");
    }

    @After("@Mobile")
    public void tearDownMobile(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("END SCENARIO (MOBILE): " + scenario.getStatus());

        TakeScreenshot(scenario);

        System.out.println("End Appium Mobile Session...");
        DriverManager.quitDriver();
        System.out.println("------------------------------------------------------------");
    }

    @After
    public void tearDownGlobal(Scenario scenario) {
        SoftAssertManager.assertAll();
    }

    private void TakeScreenshot(Scenario scenario) {
        if (scenario.isFailed() && DriverManager.getDriver() != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot_" + scenario.getName());
                System.out.println("Screenshot succeeded added inside the report report!");
            } catch (Exception e) {
                System.err.println("Failed to take screenshot: " + e.getMessage());
            }
        }
    }
}