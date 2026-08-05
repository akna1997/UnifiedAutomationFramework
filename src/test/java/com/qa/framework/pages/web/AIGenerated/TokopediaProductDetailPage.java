package com.qa.framework.pages.web.AIGenerated;

import com.qa.framework.pages.base.BasePage;
import com.qa.framework.pages.elements.AIGenerated.ProductDetailElements;
import org.openqa.selenium.WebDriver;

/**
 * Page Object untuk Product Detail Page (PDP) Tokopedia.
 * Mengikuti konvensi UAF: extends BasePage, locator hanya di elements class,
 * dan semua aksi memakai method bawaan BasePage.
 */
public class TokopediaProductDetailPage extends BasePage {

    private final ProductDetailElements elements;

    public TokopediaProductDetailPage(WebDriver driver) {
        super(driver);
        this.elements = new ProductDetailElements(driver);
    }

    public void bukaHalamanProduk(String urlProduk) {
        driver.get(urlProduk);
    }

    public boolean apakahThumbnailProdukMuncul() {
        return isDisplayed(elements.thumbnailProduk);
    }

    public int jumlahThumbnailProduk() {
        return elements.daftarThumbnailProduk.size();
    }

    public boolean apakahNamaProdukMuncul() {
        return isDisplayed(elements.namaProdukPDP);
    }

    public boolean apakahHargaProdukMuncul() {
        return isDisplayed(elements.hargaProdukPDP);
    }

    public String ambilNamaProduk() {
        isDisplayed(elements.namaProdukPDP);
        return elements.namaProdukPDP.getText().trim();
    }

    public String ambilHargaProduk() {
        isDisplayed(elements.hargaProdukPDP);
        return elements.hargaProdukPDP.getText().trim();
    }

    public void klikThumbnailPertama() {
        click(elements.thumbnailProduk);
    }
}
