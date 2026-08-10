package com.qa.framework.pages.base;

import java.time.Duration;

import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.ElementNotInteractableException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.appium.java_client.AppiumBy;
import io.appium.java_client.pagefactory.AndroidFindBy;
import java.lang.reflect.Field;

public class UtilBasePage {
    protected WebDriver driver;
    protected static final int DEFAULT_RETRIES = 2;
    protected static final Duration RETRY_PAUSE = Duration.ofMillis(300);
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    public UtilBasePage(WebDriver driver) {
        this.driver = driver;
    }

    @FunctionalInterface
    protected interface ElementAction {
        void perform() throws Exception;
    }

    protected void safeRetry(String actionName, WebElement element, ElementAction action) {
        int attempt = 0;
        WebDriverException last = null;
        while (attempt <= DEFAULT_RETRIES) {
            try {
                action.perform();
                if (attempt > 0) {
                    log.info("{} Succeed after {} times retry on {}", actionName, attempt, describe(element));
                }
                return;
            } catch (WebDriverException e) {
                if (!isRetryable(e)) {
                    throw e;
                }
                last = e;
                if (attempt >= DEFAULT_RETRIES) {
                    break;
                }
                attempt++;
                log.warn("{} Failed ({}), retried-{} times on {}", actionName, e.getClass().getSimpleName(), attempt, describe(element));
                try {
                    Thread.sleep(RETRY_PAUSE.toMillis());
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    break;
                }
            } catch (Exception e) {
                throw new RuntimeException("Failed to run " + actionName, e);
            }
        }
        throw last;
    }

    protected boolean isRetryable(WebDriverException e) {
        return e instanceof StaleElementReferenceException
                || e instanceof ElementClickInterceptedException
                || e instanceof ElementNotInteractableException;
    }

    protected String describe(WebElement element) {
        try {
            return element.toString();
        } catch (Exception e) {
            return "unknown-element";
        }
    }

    protected WebElement dynamicElement(Field field, String... args) {
        AndroidFindBy androidAnno = field.getAnnotation(AndroidFindBy.class);
        FindBy findAnno = field.getAnnotation(FindBy.class);

        String xpath = null;
        if (androidAnno != null && !androidAnno.xpath().isEmpty()) {
            xpath = androidAnno.xpath();
        } else if (findAnno != null && !findAnno.xpath().isEmpty()) {
            xpath = findAnno.xpath();
        }
        if (xpath == null || xpath.isEmpty()) {
            throw new IllegalArgumentException(
                "Field '" + field.getName() + "' xpath not found inside @AndroidFindBy/@FindBy");
        }
        String resolved = String.format(xpath, (Object[]) args);
        return driver.findElement(AppiumBy.xpath(resolved));
    }
}
