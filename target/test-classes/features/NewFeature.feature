Feature: Temporary Dummy example of datatable

  @datatable
  Scenario: Implementation of datatable
    Given I want to do something
    When I have a argument to send as "Shraddha"
    When I have a list of items to send
      | Shraddha |
      | Yash     |
      | Vishal   |
      | Kalyani  |
      | Sonam    |
      | Priya    |
    When I have a Employees Name and their Employee Id
      | Employee Name | Employee Id |
      | Shraddha      | 12345       |
      | Yash          | 34526       |
      | Vishal        | 56754       |
      | Kalyani       | 89765       |
      | Sonam         | 76587       |
      | Priya         | 78690       |
    Then Something should be happen


  @Example
  Scenario Outline: I want to search for product
    Given I am on a search page
    When I want to search for a product as "<Product>"
    Then Result should be displayed related to "<Product>"
    Examples:
      | Product   |
      | Earrings  |
      | Bracelet  |
      | Anklet    |
      | Shoes     |
      | Bluetooth |
      | I-pad     |
      | computer  |
      | Laptop    |

