
Scenario: Project - GET project conforms to schema
Given using credentials admin:secret
And the project named TestProject1 exists
When project TestProject1 is retrieved (GET)
Then the response status code is 200
And the response body conforms to schema schema/project-schema.json

Scenario: Project - GET project list conforms to schema
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

Scenario: Project - GET invalid project results in 404
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is retrieved (GET)
Then the response status code is 404

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
And project TestProject1 is initialized with members projectAdmin1
And using credentials projectAdmin1:secret
When the display name of TestProject1 is updated via PUT to New Display Name for Project
Then the response status code is 204
And project TestProject1 has a display name of New Display Name for Project 

Scenario: Project - PUT update project description as project admin
Given using credentials admin:secret
And project TestProject1 is initialized with members projectAdmin1
And using credentials projectAdmin1:secret
When the description of TestProject1 is updated via PUT to New Description for Project
Then the response status code is 204
And project TestProject1 has a description of New Description for Project

Scenario: Project - PUT update project display name as non admin is unauthorized
Given using credentials admin:secret
And project TestProject1 is initialized with members user1
And using credentials user1:secret
When the display name of TestProject1 is updated via PUT to New Display Name for Project
Then the response status code is 403

Scenario: Project - PUT update project description as non admin is unauthorized
Given using credentials admin:secret
And project TestProject1 is initialized with members user1
And using credentials user1:secret
When the description of TestProject1 is updated via PUT to New Description for Project
Then the response status code is 403

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

