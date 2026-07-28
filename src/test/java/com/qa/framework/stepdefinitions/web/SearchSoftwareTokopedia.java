package com.qa.framework.stepdefinitions.web;

import com.qa.framework.utils.ConfigReader;
import com.qa.framework.utils.DriverManager;
import org.testng.Assert;
import com.qa.framework.pages.web.TokopediaHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class SearchSoftwareTokopedia {

    private final TokopediaHomePage hompage = new TokopediaHomePage(DriverManager.getDriver());
    
    @Given("Tokopedia homepage terbuka")
    public void Tokopedia_homepage_terbuka() {
        String urlTujuan = ConfigReader.getProperty("web.url");
        DriverManager.getDriver().get(urlTujuan);
    }

    @When("pengguna mencari barang di searchbox {string}")
    public void pengguna_mencari_barang_di_searchbox(String namaBarang) {
        hompage.cariBarang(namaBarang);
    }

    @Then("sistem harus menampilkan software produk yang relevan")
    public void sistem_harus_menampilkan_software_produk_yang_relevan() {
        boolean isVisible = hompage.apakahDaftarProdukMuncul();
        Assert.assertTrue(isVisible, "Gagal! Daftar produk software tidak muncul di layar.");
    }
}
