package com.qa.framework.stepdefinitions.api;

import com.qa.framework.utils.ConfigReader;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class ApiAddProductSteps
{

    private Response response;
    private String endpoint;

    @Given("user mengatur base URL untuk tambah produk")
    public void userMengaturBaseUrlTambahProduk() {
        RestAssured.baseURI = RestAssured.baseURI = ConfigReader.getProperty("api.base.url");
        endpoint = "/products/add";
    }

    @When("user mengirim POST request dengan data barang baru")
    public void userMengirimPostRequestDenganData() {
        String payloadJSON = "{\n" +
                "  \"title\": \"Housing Filter Air 10 Inch (Clear)\",\n" +
                "  \"price\": 125000,\n" +
                "  \"description\": \"Tabung housing pengganti untuk filter air rumah tangga\",\n" +
                "  \"category\": \"home-improvement\"\n" +
                "}";

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

        System.out.println("✅ Produk berhasil ditambahkan! Berikut respon server:");
        System.out.println(response.asPrettyString());
    }
}
