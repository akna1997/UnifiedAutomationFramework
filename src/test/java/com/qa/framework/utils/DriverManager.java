package com.qa.framework.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.options.UiAutomator2Options;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.net.URL;
import java.time.Duration;

public class DriverManager {

    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    public static void initializeDriver(String platform) {
        long timeout = Long.parseLong(ConfigReader.getProperty("timeout"));

        System.out.println("Menyiapkan Driver untuk Platform: " + platform);

        if (platform.equals("web")) {
            String browserName = ConfigReader.getProperty("browser").toLowerCase();

            System.out.println("Inisialisasi browser: " + browserName);
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
                        System.out.println("🌐 Menjalankan Chrome dalam mode HEADLESS...");
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
                    throw new IllegalArgumentException("Browser tidak didukung: " + browserName);
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

                        options.setDeviceName(ConfigReader.getProperty("android.device.name"));
                        options.setAutomationName(ConfigReader.getProperty("android.automation.name"));
                        options.setApp(System.getProperty("user.dir") + "/" + ConfigReader.getProperty("android.app.path"));
                        options.setAutoGrantPermissions(true);
                        options.setNewCommandTimeout(Duration.ofSeconds(60));
                        options.setNoReset(false);
                        options.setAppPackage(ConfigReader.getProperty("android.appPackage"));
                        options.setAppActivity(ConfigReader.getProperty("android.appActivity"));

                        URL appiumUrl = new URL(ConfigReader.getProperty("appium.server.url"));

                        driver.set(new AndroidDriver(appiumUrl, options));
                    } catch (Exception e) {
                        System.out.println("ERROR: Gagal koneksi ke Appium Server!");
                        e.printStackTrace();
                        throw new RuntimeException("Appium initialization failed.");
                    }
                    break;

                case "ios":
                    System.out.println("untuk initialized ios driver");
                    break;

                default:
                    throw new IllegalArgumentException("platform tidak didukung: " + platform);
            }
        }

        getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(timeout));
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            System.out.println("Menutup browser atau apps ...");
            driver.get().quit();
            driver.remove();
        }
    }
}