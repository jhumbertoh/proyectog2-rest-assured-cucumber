Feature: REST - Redmine Rest Testing - Create project
  As a user...
  I want to...

  @ApiRest
  Scenario: Create an project - JSON
    Given System is ready to send request
    When System sends a request to create project service from redmine
      | name        | RedmineProject                  |
      | identifier  | redmineproject                  |
      | description | Redmine Project for api testing |
      | is_public   | true                            |
    Then The response status should be 201

  @ApiRest
  Scenario: Get project by id - JSON
    Given System is ready to send request
    When System sends a request to get projects by id service from redmine
      | id | 285 |
    Then The response status should be 200
    And The schema is "project_schema.json"
    And System should responds with response data of project
      | id   | 285                               |
      | name | Redmine Project Sw Testing Dragon |