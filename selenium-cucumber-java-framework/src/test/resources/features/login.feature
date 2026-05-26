Feature: Login functionality

  Scenario: Successful login

    Given user is on SauceDemo login page
    When user enters valid username and password
    Then user should be redirected to inventory page


  Scenario: Invalid login

    Given user is on SauceDemo login page
    When user enters invalid username and password
    Then error message should be displayed