package com.qa.framework.pages.mobile;

import com.qa.framework.pages.base.BasePage;
import com.qa.framework.pages.elements.SearchElements;
import org.openqa.selenium.WebDriver;

public class TokopediaHomePageMobile extends BasePage {

    private final SearchElements elements;

    public TokopediaHomePageMobile(WebDriver driver) {
        super(driver);
        this.elements = new SearchElements(driver);
    }

    public void cariBarang(String namaBarang) {
        System.out.println("Mencari barang: " + namaBarang);

        if (isDisplayed(elements.buttonGabungSekarang)) {
            pressBack();
        }

        click(elements.kolomPencarian);
        writeText(elements.kolomPencarian, namaBarang);
        pressEnter(elements.kolomPencarian);
    }

    public boolean apakahDaftarProdukMuncul() {
        return isDisplayed(elements.kontainerProduk);
    }
}