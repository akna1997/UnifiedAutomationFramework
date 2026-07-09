package com.qa.framework.stepdefinitions.hooks;

import com.qa.framework.utils.DriverManager;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

public class Hooks {

    @Before("@Web")
    public void setupWeb(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("🎬 START SCENARIO (WEB): " + scenario.getName());
        System.out.println("🤖 Membuka Browser untuk pengujian Web UI...");
        System.out.println("------------------------------------------------------------");

        System.setProperty("platform", "web");

        DriverManager.initializeDriver("web");
    }

    @Before("@Mobile")
    public void setUpMobile(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("🎬 START SCENARIO (MOBILE): " + scenario.getName());
        System.out.println("🤖 Menghubungkan ke Appium Server untuk pengujian Mobile UI...");
        System.out.println("------------------------------------------------------------");

        System.setProperty("platform", "android");

        DriverManager.initializeDriver("android");
    }

    @After("@Web")
    public void tearDownWeb(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("🛑 END SCENARIO (WEB): " + scenario.getStatus());

        TakeScreenshot(scenario);

        System.out.println("🚪 Menutup Web Browser...");
        DriverManager.quitDriver();
        System.out.println("------------------------------------------------------------");
    }

    @After("@Mobile")
    public void tearDownMobile(Scenario scenario) {
        System.out.println("------------------------------------------------------------");
        System.out.println("🛑 END SCENARIO (MOBILE): " + scenario.getStatus());

        TakeScreenshot(scenario);

        System.out.println("🚪 Mematikan Sesi Appium Mobile...");
        DriverManager.quitDriver();
        System.out.println("------------------------------------------------------------");
    }

    private void TakeScreenshot(Scenario scenario) {
        if (DriverManager.getDriver() != null) {
            try {
                byte[] screenshot = ((TakesScreenshot) DriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
                scenario.attach(screenshot, "image/png", "Screenshot_Gagal_" + scenario.getName());
                System.out.println("Screenshot berhasil dilampirkan ke report!");
            } catch (Exception e) {
                System.err.println("Gagal mengambil screenshot: " + e.getMessage());
            }
        }
    }
}