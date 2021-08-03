Feature: Open New Account Feature

  @open_new_account
  Scenario: User is able to open new account
    Given User is logged in
    And User is able to click on link name as "Open New Account"
    When User select Account Type as "SAVINGS" and any account number
    And User click on OPEN NEW ACCOUNT button
    Then Message is displayed Congratulations, your account is now open.
    And A new account number is generated

