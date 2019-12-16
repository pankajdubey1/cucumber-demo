@tasktodosmoke
Feature: As a End user of the api I want to verify the json response

  Scenario Outline: Verify the api gives response as per the request parameters
    When I send http request using json <JsonRequest>
    Then I verify the response code
    And I validate fields in response of webservice

    Examples: 
      | JsonRequest      |
      | postRequest.json |
      | getRequest.json  |
