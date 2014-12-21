Scenario: Project - DELETE project as global admin
Given using credentials admin:secret
And the project named TestProject1 exists
When project TestProject1 is deleted
Then the response status code is 204
And project TestProject1 is not found