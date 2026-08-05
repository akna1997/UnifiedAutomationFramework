package com.qa.framework.stepdefinitions.web.AIGenerated;

import com.qa.framework.pages.web.AIGenerated.TokopediaProductDetailPage;
import com.qa.framework.utils.DriverManager;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

/**
 * StepDefinition untuk skenario Product Detail Page (PDP) Tokopedia.
 * StepDef tipis: hanya memanggil method Page Object, assertion pakai TestNG.
 */
public class ProductDetailSteps {

    private TokopediaProductDetailPage pdpPage;

    private TokopediaProductDetailPage pdp() {
        if (pdpPage == null) {
            pdpPage = new TokopediaProductDetailPage(DriverManager.getDriver());
        }
        return pdpPage;
    }

    @Given("pengguna membuka halaman detail produk {string}")
    public void pengguna_membuka_halaman_detail_produk(String urlProduk) {
        pdp().bukaHalamanProduk(urlProduk);
    }

    @Then("thumbnail produk harus tampil di halaman detail")
    public void thumbnail_produk_harus_tampil_di_halaman_detail() {
        Assert.assertTrue(pdp().apakahThumbnailProdukMuncul(),
                "Gagal! Thumbnail produk tidak tampil di PDP.");
        Assert.assertTrue(pdp().jumlahThumbnailProduk() >= 1,
                "Gagal! Jumlah thumbnail produk kurang dari 1.");
    }

    @Then("nama produk harus tampil di halaman detail")
    public void nama_produk_harus_tampil_di_halaman_detail() {
        Assert.assertTrue(pdp().apakahNamaProdukMuncul(),
                "Gagal! Nama produk tidak tampil di PDP.");
        Assert.assertFalse(pdp().ambilNamaProduk().isEmpty(),
                "Gagal! Nama produk kosong.");
    }

    @Then("harga produk harus tampil dengan format rupiah")
    public void harga_produk_harus_tampil_dengan_format_rupiah() {
        Assert.assertTrue(pdp().apakahHargaProdukMuncul(),
                "Gagal! Harga produk tidak tampil di PDP.");
        String harga = pdp().ambilHargaProduk();
        Assert.assertTrue(harga.startsWith("Rp"),
                "Gagal! Format harga tidak diawali 'Rp'. Harga terbaca: " + harga);
    }

    @Then("nama produk harus mengandung kata {string}")
    public void nama_produk_harus_mengandung_kata(String kataKunci) {
        String namaProduk = pdp().ambilNamaProduk().toLowerCase();
        Assert.assertTrue(namaProduk.contains(kataKunci.toLowerCase()),
                "Gagal! Nama produk '" + namaProduk + "' tidak mengandung kata '" + kataKunci + "'.");
    }

    @When("pengguna mengklik thumbnail produk pertama")
    public void pengguna_mengklik_thumbnail_produk_pertama() {
        pdp().klikThumbnailPertama();
    }
}
