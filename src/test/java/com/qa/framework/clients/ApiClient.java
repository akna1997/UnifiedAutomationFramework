package com.qa.framework.clients;

import com.qa.framework.utils.ConfigReader;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class ApiClient {
    private final String baseUri;

    public ApiClient() {
        this.baseUri = ConfigReader.getProperty("api.base.url");
        RestAssured.baseURI = this.baseUri;
    }

    public Response get(String endpoint) {
        return given().header("Content-Type", "application/json")
                .when().get(endpoint);
    }

    public Response post(String endpoint, String body) {
        return given().header("Content-Type", "application/json")
                .body(body).when().post(endpoint);
    }
}
