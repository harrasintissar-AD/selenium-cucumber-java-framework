Feature: Login functionality

  Scenario: Register new user and login successfully
   
    Given user opens ParaBank application
    When user registers with valid details
    Then user should see registration successful message
    Then user should be logged out successfully
    
  Scenario: Successful login

    Given user is on Parabank login page
    When user enters valid credentials
    And user clicks login button
    Then user should be redirected to account overview page
    Then user should be logged out successfully