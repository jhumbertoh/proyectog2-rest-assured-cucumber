package com.company.cucumber.stepdefs;

import com.company.cucumber.config.RedmineEndpoints;
import com.company.entities.Entity;
import com.company.entities.Project;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import org.junit.Assert;

import java.util.Map;
import java.util.Random;

public class RestCreateProjectStepdefs {

    private RestCommonStepDefs commonSteps;

    public RestCreateProjectStepdefs(RestCommonStepDefs commonSteps) {
        this.commonSteps = commonSteps;
    }

    @When("System sends a request to create project service from redmine")
    public void systemSendsARequestToCreateProjectServiceFromRedmine(Map<String, String> projectData) {

        Integer randomNumber = (new Random()).nextInt(900000) + 100000;

        Project project = new Project();
        project.setName(projectData.get("name") + randomNumber);
        project.setIdentifier(projectData.get("identifier") + randomNumber);
        project.setDescription(projectData.get("description"));
        project.setIs_public(Boolean.parseBoolean(projectData.get("is_public")));
        project.setInherit_members(Boolean.parseBoolean(projectData.get("inherit_members")));


        Entity entity = new Entity(project);

        commonSteps.response = commonSteps.request
                .body(entity)
                .when()
                .post(RedmineEndpoints.LISTAR_REDMINE_PROJECTS_JSON);
    }

    @When("System sends a request to get projects by id service from redmine")
    public void systemSendsARequestToGetProjectsByIdServiceFromRedmine(Map<String, String> issue) {

        commonSteps.response = commonSteps.request.
                pathParam("idProject", issue.get("id")).
                when()
                .get(RedmineEndpoints.OBTENER_REDMINE_PROJECT_JSON);

    }

    @And("System should responds with response data of project")
    public void systemShouldRespondsWithResponseDataOfProject(Map<String, String> expectedData) {

        JsonPath actualData = new JsonPath(commonSteps.response.getBody().asString());

        Assert.assertEquals("El id no es el correcto", Integer.parseInt(expectedData.get("id")), actualData.getInt("project.id"));
        Assert.assertEquals("En nombre no es el correcto", expectedData.get("name"), actualData.getString("project.name"));


        //Deserialización JSON -> Objeto Java
        Entity entity = commonSteps.response.getBody().as(Entity.class);
        Project project = entity.getProject();

        System.out.println(project.toString());
    }
}