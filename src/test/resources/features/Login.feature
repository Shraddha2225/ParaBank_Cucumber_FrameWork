Feature: Login Feature

  @t1
  Scenario: User is able to Login in the application
    Given User opened the browser
    And User navigated to the application url
    #When User enter username as "john" and password as "demo" and click on login button
    When User Enter Username and Password as in the below table and click on login button
      | Username | john |
      | Password | demo |
    Then user is able to login in the application