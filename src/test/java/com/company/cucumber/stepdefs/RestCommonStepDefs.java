package com.company.cucumber.stepdefs;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class RestCommonStepDefs {

    protected RequestSpecification request;
    protected Response response;

    @Given("System is ready to send request")
    public void systemIsReadyToSendRequest() {

        request = given();
    }

    @Then("The response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {

        response.then()
                .log()
                .all()
                .statusCode(statusCode);
    }

    @And("The schema is {string}")
    public void theSchemaIs(String schema) {

        response.
                then().
                body(matchesJsonSchemaInClasspath(schema));


    }
}
