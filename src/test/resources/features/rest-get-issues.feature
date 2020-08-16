Feature: REST - Redmine Rest Testing - Get issues
  As a user...
  I want to...

  @ApiRest
  Scenario: Get issues by list - JSON
    Given System is ready to send request
    When System sends a request to list issues service from redmine json
    Then The response status should be 200

  @ApiRest
  Scenario: Get issues by id - JSON
    Given System is ready to send request
    When System sends a request to get issues by id service from redmine
      | id | 840 |
    Then The response status should be 200
    And System should responds with response data
      | id          | 840                                                   |
      | subject     | I cannot create a user.                               |
      | description | As an admin user, I cannot create an user when xml... |
      | start_date  | 2020-08-15                                            |
