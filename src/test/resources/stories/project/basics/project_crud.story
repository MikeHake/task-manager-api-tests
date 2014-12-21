
Scenario: Project - GET single project details as global admin and verify schema
Given using credentials admin:secret
And the project named TestProject1 exists
When project TestProject1 is retrieved (GET)
Then the response status code is 200
And the response body conforms to schema schema/project-schema.json

Scenario: Project - GET project list as global admin and verify schema
Given using credentials admin:secret
And the project named TestProject1 exists
And the project named TestProject2 exists
When the list of all projects is retrieved (GET)
Then the response status code is 200
And the response body conforms to schema schema/projects-schema.json

Scenario: Project - POST new project as global admin
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is created (POST)
Then the response status code is 201
And the response body conforms to schema schema/project-schema.json

Scenario: Project - DELETE project as global admin
Given using credentials admin:secret
And the project named TestProject1 exists
When project TestProject1 is deleted
Then the response status code is 204
And project TestProject1 is not found

Scenario: Project - PUT update project display name as global admin
Given using credentials admin:secret
And project TestProject1 is recreated in default state
When the display name of TestProject1 is updated via PUT to New Display Name for Project
Then the response status code is 204
And project TestProject1 has a display name of New Display Name for Project 

Scenario: Project - PUT update project description as global admin
Given using credentials admin:secret
And project TestProject1 is recreated in default state
When the description of TestProject1 is updated via PUT to New Description for Project
Then the response status code is 204
And project TestProject1 has a description of New Description for Project

Scenario: Project - PUT update project display name as project admin
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials projectAdmin1:secret
When the display name of TestProject1 is updated via PUT to New Display Name for Project
Then the response status code is 204
And project TestProject1 has a display name of New Display Name for Project 

Scenario: Project - PUT update project description as project admin
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials projectAdmin1:secret
When the description of TestProject1 is updated via PUT to New Description for Project
Then the response status code is 204
And project TestProject1 has a description of New Description for Project

Scenario: Project - GET single project details as user on project results in success
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials user1:secret
When project TestProject1 is retrieved (GET)
Then the response status code is 200
And the response body conforms to schema schema/project-schema.json

Scenario: Project - GET all projects as global admin returns all
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And project TestProject2 is recreated with members user1 and admins projectAdmin1
When the list of all projects is retrieved (GET)
Then the response status code is 200
And the response project list contains project TestProject1
And the response project list contains project TestProject2

Scenario: Project - GET all projects user returns only projects user is on
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And project TestProject2 is recreated with members user2 and admins projectAdmin1
And using credentials user1:secret
When the list of all projects is retrieved (GET)
Then the response status code is 200
And the response project list contains project TestProject1
And the response project list does not contain project TestProject2



