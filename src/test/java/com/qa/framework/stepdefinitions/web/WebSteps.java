package com.qa.framework.stepdefinitions.web;

import com.qa.framework.utils.ConfigReader;
import com.qa.framework.utils.DriverManager;
import com.qa.framework.pages.web.TokopediaHomePage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class WebSteps {

    private final TokopediaHomePage hompage = new TokopediaHomePage(DriverManager.getDriver());

    @Given("pengguna membuka halaman utama {string}")
    public void pengguna_membuka_halaman_utama(String namaToko) {
        String urlTujuan = ConfigReader.getProperty("web.url");
        DriverManager.getDriver().get(urlTujuan);
    }

    @When("pengguna mencari barang {string}")
    public void pengguna_mencari_barang(String namaBarang) {
        hompage.cariBarang(namaBarang);
    }

    @Then("sistem harus menampilkan daftar produk yang relevan")
    public void sistem_harus_menampilkan_daftar_produk_yang_relevan() {
        boolean isVisible = hompage.apakahDaftarProdukMuncul();
        Assert.assertTrue(isVisible, "Gagal! Daftar produk tidak muncul di layar.");

    }
}