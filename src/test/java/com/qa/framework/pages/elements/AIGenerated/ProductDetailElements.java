package com.qa.framework.pages.elements.AIGenerated;

import io.appium.java_client.pagefactory.AndroidFindBy;
import io.appium.java_client.pagefactory.AppiumFieldDecorator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;
import java.util.List;

/**
 * Elements gabungan untuk halaman Product Detail Page (PDP) Tokopedia.
 * Satu class untuk web (@FindBy) dan mobile (@AndroidFindBy) - AppiumFieldDecorator
 * otomatis memilih annotation sesuai driver yang masuk.
 *
 * Selector web di bawah ini SUDAH DIVERIFIKASI langsung di browser (powerful mode)
 * pada 3 PDP berbeda, tanggal 2026-08-03.
 */
public class ProductDetailElements {

    public ProductDetailElements(WebDriver driver) {
        PageFactory.initElements(new AppiumFieldDecorator(driver, Duration.ofSeconds(10)), this);
    }

    // Thumbnail galeri gambar produk (terverifikasi: count 9 / 7 / 3 tergantung produk)
    @FindBy(css = "button[data-testid='PDPImageThumbnail']")
    @AndroidFindBy(xpath = "//*[contains(@resource-id,'PDPImageThumbnail') or contains(@resource-id,'product_image')]")
    public WebElement thumbnailProduk;

    // Semua thumbnail - dipakai untuk hitung jumlah gambar
    @FindBy(css = "button[data-testid='PDPImageThumbnail']")
    @AndroidFindBy(xpath = "//*[contains(@resource-id,'PDPImageThumbnail')]")
    public List<WebElement> daftarThumbnailProduk;

    // Nama produk di PDP (H1, terverifikasi count=1)
    @FindBy(css = "h1[data-testid='lblPDPDetailProductName']")
    @AndroidFindBy(xpath = "//*[contains(@resource-id,'pdp_product_name') or contains(@resource-id,'txt_product_name')]")
    public WebElement namaProdukPDP;

    // Harga utama produk di PDP (terverifikasi: Rp275.900 / Rp77.600 / Rp185.000)
    @FindBy(css = "div[data-testid='lblPDPDetailProductPrice']")
    @AndroidFindBy(xpath = "//*[contains(@resource-id,'pdp_product_price') or contains(@resource-id,'txt_product_price')]")
    public WebElement hargaProdukPDP;
}
