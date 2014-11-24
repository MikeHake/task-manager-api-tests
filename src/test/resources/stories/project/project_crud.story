

Scenario: Project - Create new project as global admin
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is created (POST)
Then the response status code is 201
And a GET can be performed to retrieve project TestProject1

Scenario: Project - Delete project as global admin
Given using credentials admin:secret
And the project named TestProject2 exists
When project TestProject2 is deleted
Then the response status code is 204
And project TestProject2 is not found