Feature:  Customer Authentication


  Scenario: Customer successfully registers a new account
   
    Given user opens ParaBank application
    When user registers with valid details
    And user clicks on register button
    Then user should see registration successful message
    Then user should be logged out successfully

  Scenario: Customer logs in with valid credentials

    Given user is on Parabank login page
    When user enters valid credentials
    And user clicks on login button
    Then user should be redirected to account overview page
    Then user should be logged out successfully


  Scenario: Customer fails to login with invalid credentials
    Given user is on Parabank login page
    When the customer enters username "invalidUser" and password "invalidPassword"
    And user clicks on login button
    Then a login error message should be displayed


  Scenario Outline: Customer registration validation
    Given user opens ParaBank application
    When the customer registers with invalid data:
      | firstName       | <firstName>       |
      | lastName        | <lastName>        |
      | address         | <address>         |
      | city            | London            |
      | state           | Canada            |
      | zipCode         | K11111            |
      | phoneNumber     | 1234567890        |
      | ssn             | 123456789         |
      | username        | <username>        |
      | password        | Password123       |
      | confirmPassword | Password123       |
    And user clicks on register button
    Then the registration validation message "<errorMessage>" should be displayed

    Examples:
      | firstName | lastName | address      | username | errorMessage            |
      |           | Luna   | Bank street  | user1001 | First name is required. |
      | Victoria  |          | Bank street  | user1002 | Last name is required.  |
      | Victoria  | Luna   |              | user1003 | Address is required.    |
      | Victoria  | Luna   | Bank street  |          | Username is required.   |
