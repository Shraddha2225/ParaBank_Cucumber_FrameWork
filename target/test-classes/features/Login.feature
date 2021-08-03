Feature: Login Feature


  Scenario: User is able to Login in the application
    Given User opened the browser
    And User navigated to the application url
    When User enter username as "john" and password as "demo" and click on login button
    Then user is able to login in the application