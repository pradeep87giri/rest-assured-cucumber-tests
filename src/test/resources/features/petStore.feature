Feature: Retrieve pets by status

  Background:
    Given I set the URI to base url


  Scenario Outline: Find all pets by status for a category
    When I set the path to "<path>"
    And I set the method to GET
    And I set the parameters with the below data
      | Param  | Value    |
      | status | <status> |
    And I execute the request
    Then The status code is "200"
    And I retrieve all "<status>" pets of category "<category>"
    And The response time is less than "<expectedResponseTime>" ms

    Examples:
      | path              | status    | category | expectedResponseTime |
      | /pet/findByStatus | available | Lions    | 3000                 |
      | /pet/findByStatus | pending   | Lions    | 3000                 |
      | /pet/findByStatus | sold      | Dogs     | 3000                 |


  Scenario Outline: Attempt to find pets with invalid status
    When I set the path to "<path>"
    And I set the method to GET
    And I set the parameters with the below data
      | Param  | Value    |
      | status | <status> |
    And I execute the request
    Then The status code is "400"
    And I verify the "<status>" is invalid

    Examples:
      | path              | status       |
      | /pet/findByStatus | notAvailable |
      | /pet/findByStatus |              |


  Scenario Outline: Attempt to find pets without status
    When I set the path to "<path>"
    And I set the method to GET
    And I execute the request
    Then The status code is "400"
    And I verify the error msg on not providing status

    Examples:
      | path              |
      | /pet/findByStatus |


  Scenario Outline: Attempt to find pets with invalid path
    When I set the path to "<path>"
    And I set the method to GET
    And I execute the request
    Then The status code is "404"

    Examples:
      | path             |
      | /pet/findByStyle |