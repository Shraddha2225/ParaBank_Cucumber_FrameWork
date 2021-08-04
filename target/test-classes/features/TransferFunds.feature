Feature: Transfer Funds Feature

  @transfer_funds
  Scenario: User is able to transfer funds
    Given User is logged in
    And User is able to click on link name as "Transfer Funds"
    When User enter amount "3600" to transfer
    And User select any account number and want to transfer funds to any another account
    And User click on TRANSFER
    Then Message is displayed amount is transfer from one account number to another account number

