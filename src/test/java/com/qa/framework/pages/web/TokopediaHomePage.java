package com.qa.framework.pages.web;

import com.qa.framework.pages.base.BasePage;
import com.qa.framework.pages.elements.SearchElements;
import org.openqa.selenium.WebDriver;
import java.util.Objects;

public class TokopediaHomePage extends BasePage {

    private final SearchElements elements;

    public TokopediaHomePage(WebDriver driver) {
        super(driver);
        this.elements = new SearchElements(driver);
    }

    public void cariBarang(String namaBarang) {
        System.out.println("Mencari barang: " + namaBarang);

        writeText(elements.kolomPencarian, namaBarang);
        pressEnter(elements.kolomPencarian);
    }

    public boolean apakahDaftarProdukMuncul() {
        return isDisplayed(elements.kontainerProduk);
    }

    public boolean searchBoxText(String textNamaBarang) {
        String searchboxText = elements.kolomPencarian.getAttribute("value");
        return Objects.equals(searchboxText ,textNamaBarang);
    }
}