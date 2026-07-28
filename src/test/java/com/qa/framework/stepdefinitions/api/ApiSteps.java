package com.qa.framework.stepdefinitions.api;

import com.qa.framework.pages.base.BasePage;
import com.qa.framework.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiSteps {

    private Response response;
    private String endpoint;
    private static final Logger log = LoggerFactory.getLogger(BasePage.class);

    @Given("user mengatur endpoint API pencarian")
    public void userMengaturEndpointApi() {
        RestAssured.baseURI = ConfigReader.getProperty("api.base.url");
        endpoint = "/products/search";
    }

    @When("user mengirim GET request dengan kata kunci {string}")
    public void userMengirimGetRequest(String keyword) {
        response = given()
                .queryParam("q", keyword)
                .header("Accept", "application/json")
                .when()
                .get(endpoint);
    }

    @Then("status code API Read harus {int}")
    public void statusCodeApiReadHarus(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("respon API harus menampilkan minimal {int} data produk")
    public void responApiHarusMenampilkanDataProduk(int minimalData) {
        response.then().body("products.size()", greaterThanOrEqualTo(minimalData));

        log.info("Response from server:");
        System.out.println(response.asPrettyString());
    }
}