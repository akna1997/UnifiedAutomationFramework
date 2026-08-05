package com.qa.framework.stepdefinitions.api;

import com.qa.framework.clients.ApiClient;
import com.qa.framework.utils.TestContext;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.response.Response;
import static org.hamcrest.Matchers.*;
import java.io.InputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ApiAddProductSteps
{

    private Response response;
    private String endpoint;
    private String payloadJSON;
    private static final Logger log = LoggerFactory.getLogger(ApiSteps.class);
    private final TestContext context;
    private final ApiClient client;

    public ApiAddProductSteps(TestContext context, ApiClient client) {
        this.context = context;
        this.client = client;
    }

    @Given("user mengatur base URL untuk tambah produk")
    public void userMengaturBaseUrlTambahProduk() {
        endpoint = "/products/add";
    }

    @When("user mengirim POST request dengan data barang baru")
    public void userMengirimPostRequestDenganData() throws Exception {
        InputStream is = getClass().getClassLoader().getResourceAsStream("testdata/api_product.json");
        payloadJSON = new String(is.readAllBytes());

        response = client.post(endpoint, payloadJSON);
        context.set("addProductResponse", response);
    }

    @Then("status code API ADD harus {int}")
    public void statusCodeApiAddHarus(int expectedStatusCode) {
        response.then().statusCode(expectedStatusCode);
    }

    @Then("respon API harus mengembalikan ID produk yang baru dibuat")
    public void responApiHarusMengembalikanId() {
        response.then().body("id", notNullValue());

        log.info("Request from client:");
        System.out.println(payloadJSON);
        log.info("Response from server:");
        System.out.println(response.asPrettyString());
    }
}
