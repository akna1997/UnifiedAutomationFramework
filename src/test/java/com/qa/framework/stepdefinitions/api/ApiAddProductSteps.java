package com.qa.framework.stepdefinitions.api;

import com.qa.framework.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import java.nio.file.Paths;
import java.nio.file.Files;

public class ApiAddProductSteps
{

    private Response response;
    private String endpoint;
    private String payloadJSON;

    @Given("user mengatur base URL untuk tambah produk")
    public void userMengaturBaseUrlTambahProduk() {
        RestAssured.baseURI = ConfigReader.getProperty("api.base.url");
        endpoint = "/products/add";
    }

    @When("user mengirim POST request dengan data barang baru")
    public void userMengirimPostRequestDenganData() throws Exception {
         payloadJSON = new String(Files.readAllBytes(Paths.get("src/test/resources/testdata/api_product.json")));

        response = given()
                .header("Content-Type", "application/json")
                .body(payloadJSON)
                .when()
                .post(endpoint);
    }

    @Then("status code API ADD harus {int}")
    public void statusCodeApiAddHarus(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("respon API harus mengembalikan ID produk yang baru dibuat")
    public void responApiHarusMengembalikanId() {
        response.then().body("id", notNullValue());

        System.out.println("Produk berhasil ditambahkan! Berikut request dari server:");
        System.out.println(payloadJSON);
        System.out.println("Produk berhasil ditambahkan! Berikut respon server:");
        System.out.println(response.asPrettyString());
    }
}
