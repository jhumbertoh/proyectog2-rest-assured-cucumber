package com.company.stepdefs;


import com.company.config.RedmineEndpoints;
import com.company.config.RedmineEnvironments;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.util.Map;

public class RestGetIssuesStepdefs {


    private RestCommonStepDefs commonSteps;

    public RestGetIssuesStepdefs(RestCommonStepDefs commonSteps){
        this.commonSteps = commonSteps;
    }


    @When("System sends a request to list issues service from redmine json")
    public void systemSendsARequestToListIssuesServiceFromRedmineJson() {

        commonSteps.response = commonSteps.request.when()
                .get( RedmineEndpoints.LISTAR_REDMINE_ISSUES_JSON);
    }


    @When("System sends a request to get issues by id service from redmine")
    public void systemSendsARequestToGetIssuesByIdServiceFromRedmine(Map<String, String> issue) {


        commonSteps.response = commonSteps.request.
                pathParam("idIssue", issue.get("id")).
        when()
                .get(RedmineEndpoints.OBTENER_REDMINE_ISSUE_JSON);

    }

    @And("System should responds with response data")
    public void systemShouldRespondsWithResponseData(Map<String, String> expectedData) {

        JsonPath actualData = new JsonPath(commonSteps.response.getBody().asString());

        Assert.assertEquals("El id no es el correcto",Integer.parseInt(expectedData.get("id")), actualData.getInt("issue.id"));
        Assert.assertEquals(expectedData.get("subject"), actualData.getString("issue.subject"));
        Assert.assertEquals(expectedData.get("description"), actualData.getString("issue.description"));
        Assert.assertEquals(expectedData.get("start_date"), actualData.getString("issue.start_date"));

    }
}