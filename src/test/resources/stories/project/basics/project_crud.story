

Scenario: Project - POST new project as global admin
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is created (POST)
Then the response status code is 201
And the response body conforms to schema schema/project-schema.json

Scenario: Project - DELETE project as global admin
Given using credentials admin:secret
And the project named TestProject2 exists
When project TestProject2 is deleted
Then the response status code is 204
And project TestProject2 is not found

Scenario: Project - GET invalid project results in 404
Given this scenario is pending

Scenario: Project - PUT update project display name as global admin
Given this scenario is pending

Scenario: Project - PUT update project description as global admin
Given this scenario is pending

Scenario: Project - GET all projects as global admin returns all
Given this scenario is pending

Scenario: Project - GET all projects user returns only projects user is on
Given this scenario is pending

Scenario: Project - GET single project details as global admin
Given this scenario is pending

Scenario: Project - GET single project details as user on project
Given this scenario is pending

Scenario: Project - GET single project details as user NOT on project results in 401
Given this scenario is pending

