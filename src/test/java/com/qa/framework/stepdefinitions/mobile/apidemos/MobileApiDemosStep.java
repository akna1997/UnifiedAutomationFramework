package com.qa.framework.stepdefinitions.mobile.apidemos;

import com.qa.framework.pages.mobile.ApiDemosApp.ApiDemosAppPageMobile;
import com.qa.framework.utils.DriverManager;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.testng.Assert;

public class MobileApiDemosStep {
    private final ApiDemosAppPageMobile mobilePage = new ApiDemosAppPageMobile(DriverManager.getDriver());

    @Given("Mobile user membuka halaman utama {string}")
    public void Mobile_pengguna_membuka_halaman_utama(String namaToko) {
        DriverManager.getDriver();
    }
    @When("Mobile user click menu {string}")
    public void Mobile_pengguna_click_menu(String menu) {
        mobilePage.clickMenu(menu);
    }

    @Then("Mobile sistem harus menampilkan text {string}")
    public void Mobile_sistem_harus_menampilkan_text(String elementText) {
        Assert.assertEquals(mobilePage.getTextApiDemos(), elementText);
    }

}
