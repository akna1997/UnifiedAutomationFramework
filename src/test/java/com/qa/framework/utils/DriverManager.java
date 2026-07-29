package com.qa.framework.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);

    public static void initializeDriver(String platform) {
        log.info("Getting driver ready for Platform: {}", platform);

        if (platform.equals("Web")) {
            String browserName = ConfigReader.getProperty("browser").toLowerCase();

            log.info("Inisialisasi browser: {}", browserName);
            String isHeadless = System.getProperty("headless", "false");

            switch (browserName) {
                case "chrome":
                    ChromeOptions chromeOptions = new ChromeOptions();

                    if (isHeadless.equalsIgnoreCase("true")) {
                        chromeOptions.addArguments("--headless=new");
                        chromeOptions.addArguments("--disable-gpu");
                        chromeOptions.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/124.0.0.0 Safari/537.36");
                        chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
                        chromeOptions.addArguments("--incognito");
                        chromeOptions.addArguments("--window-size=1920,1080");
                        log.info("Run {} browser in headless mode ...", browserName);
                    }
                    chromeOptions.addArguments("--remote-allow-origins=*");
                    chromeOptions.addArguments("--start-maximized"); // Buka layar penuh
                    driver.set(new ChromeDriver(chromeOptions));
                    break;

                case "firefox":
                    FirefoxOptions firefoxOptions = new FirefoxOptions();
                    if (isHeadless.equalsIgnoreCase("true")) {
                        firefoxOptions.addArguments("--headless");
                    }
                    driver.set(new FirefoxDriver(firefoxOptions));
                    break;

                default:
                    throw new IllegalArgumentException("Browser not supported: " + browserName);
            }
        } else {
            switch (platform) {
                case "android":
                    try {
                        UiAutomator2Options options = new UiAutomator2Options();

                        String udid = ConfigReader.getProperty("android.udid");
                        if (!udid.isEmpty()) {
                            options.setUdid(udid);
                        }

                        // options.setDeviceName(ConfigReader.getProperty("android.device.name"));
                        // options.setApp(System.getProperty("user.dir") + "/" + ConfigReader.getProperty("android.app.path"));
                        options.setAutomationName(ConfigReader.getProperty("android.automation.name"));
                        options.setAutoGrantPermissions(true);
                        options.setNewCommandTimeout(Duration.ofSeconds(60));
                        options.setNoReset(false);
                        options.setAppPackage(ConfigReader.getProperty("android.appPackage"));
                        options.setAppActivity(ConfigReader.getProperty("android.appActivity"));

                        URL appiumUrl = new URL(ConfigReader.getProperty("appium.server.url"));

                        driver.set(new AndroidDriver(appiumUrl, options));
                    } catch (Exception e) {
                        log.error("ERROR: Failed connect to Appium Server!");
                        e.printStackTrace();
                        throw new RuntimeException("Appium initialization failed.");
                    }
                    break;

                case "ios":
                    System.out.println("for initialized ios driver");
                    break;

                default:
                    throw new IllegalArgumentException("platform not supported: " + platform);
            }
        }

        // getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout)); <- jgn pake implicit Wait dan explixit wait barengan
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            log.info("Closing browser or apps ...");
            driver.get().quit();
            driver.remove();
        }
    }
}