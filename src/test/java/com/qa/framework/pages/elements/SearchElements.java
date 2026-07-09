package com.qa.framework.pages.elements;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class SearchElements {

    public SearchElements(WebDriver driver) {
        // AppiumFieldDecorator akan mendeteksi secara otomatis driver yang masuk.
        // Jika yang masuk ChromeDriver, dia pakai @FindBy. Jika AndroidDriver, dia pakai @AndroidFindBy.
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    @FindBy(xpath = "//input[@aria-label='Cari di Tokopedia']")
    @AndroidFindBy(xpath = "//android.widget.EditText[contains(@resource-id,'searchTextView') or contains(@resource-id, 'et_search')]")
    public WebElement kolomPencarian;

    @FindBy(xpath = "//div[@data-testid='imgLeg-c']")
    @AndroidFindBy(xpath = "(//*[contains(@resource-id,'searchProductCardGridReimagine')])[1]")
    public WebElement kontainerProduk;

    @AndroidFindBy(xpath = "//*[@text='Gabung Sekarang']")
    public WebElement buttonGabungSekarang;
}