Feature: Bill Pay Feature

  @bill_payment
  Scenario: User is able to pay a bill
    Given User is logged in
    And User is able to click on link name as "Bill Pay"
    When User enter payee name and address
    And User enter city and state
    And User enter zipcode and phone number
    And User enter account number and verify account number
    And User enter an amount
    And User select any account number from which he want to pay a bill
    And User click on SEND PAYMENT
    Then Message is displayed bill payment was successful



    

