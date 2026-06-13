Feature:  Customer Authentication

  Scenario: Customer successfully registers a new account
   
    Given user opens ParaBank application
    When user registers with valid details
    Then user should see registration successful message
    Then user should be logged out successfully
    
  Scenario: Customer logs in with valid credentials

    Given user is on Parabank login page
    When user enters valid credentials
    And user clicks login button
    Then user should be redirected to account overview page
    Then user should be logged out successfully