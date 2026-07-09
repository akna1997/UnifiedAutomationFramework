package com.qa.framework.stepdefinitions.mobile;

import com.qa.framework.pages.mobile.TokopediaHomePageMobile;
import com.qa.framework.utils.ConfigReader;
import com.qa.framework.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class MobileSteps {

    private final TokopediaHomePageMobile hompage = new TokopediaHomePageMobile(DriverManager.getDriver());

    @Given("Mobile pengguna membuka halaman utama {string}")
    public void Mobile_pengguna_membuka_halaman_utama_Mobile(String namaToko) {
        String urlTujuan = ConfigReader.getProperty("web.url");
        DriverManager.getDriver().get(urlTujuan);
    }

    @When("Mobile pengguna mencari barang {string}")
    public void Mobile_pengguna_mencari_barang(String namaBarang) {
        hompage.cariBarang(namaBarang);
    }

    @Then("Mobile sistem harus menampilkan daftar produk yang relevan")
    public void Mobile_sistem_harus_menampilkan_daftar_produk_yang_relevan() {
        boolean isVisible = hompage.apakahDaftarProdukMuncul();
        Assert.assertTrue(isVisible, "Gagal! Daftar produk tidak muncul di layar.");

    }
}