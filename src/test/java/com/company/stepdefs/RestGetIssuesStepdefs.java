package com.company.stepdefs;


import com.company.config.RedmineEndpoints;
import com.company.config.RedmineEnvironments;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.Assert;


import java.util.Map;

import static io.restassured.RestAssured.given;

public class RestGetIssuesStepdefs {

    private RequestSpecification request;
    private Response response;

    @Given("System is ready to send request")
    public void systemIsReadyToSendRequest() {

        request = given();
    }

    @When("System sends a request to list issues service from redmine json")
    public void systemSendsARequestToListIssuesServiceFromRedmineJson() {

        response = request.when()
                .get(RedmineEnvironments.REDMINE_LOCAL + ":"+RedmineEnvironments.REDMINE_LOCAL_PORT +"/"+ RedmineEndpoints.LISTAR_REDMINE_ISSUES_JSON);

    }

    @Then("The response status should be {int}")
    public void theResponseStatusShouldBe(int statusCode) {

        response.then()
                .log()
                .all()
                .statusCode(statusCode);
    }

    @When("System sends a request to get issues by id service from redmine")
    public void systemSendsARequestToGetIssuesByIdServiceFromRedmine(Map<String, String> issue) {

        String url = RedmineEnvironments.REDMINE_LOCAL + ":"+RedmineEnvironments.REDMINE_LOCAL_PORT;

                response = request.
                pathParam("idIssue", issue.get("id")).
        when()
                .get(url + RedmineEndpoints.OBTENER_REDMINE_ISSUE_JSON);

    }

    @And("System should responds with response data")
    public void systemShouldRespondsWithResponseData(Map<String, String> expectedData) {

        JsonPath actualData = new JsonPath(response.getBody().asString());

        Assert.assertEquals("El id no es el correcto",Integer.parseInt(expectedData.get("id")), actualData.getInt("issue.id"));
        Assert.assertEquals(expectedData.get("subject"), actualData.getString("issue.subject"));
        Assert.assertEquals(expectedData.get("description"), actualData.getString("issue.description"));
        Assert.assertEquals(expectedData.get("start_date"), actualData.getString("issue.start_date"));

    }
}