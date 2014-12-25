Scenario: Project - POST new project as global admin
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is created (POST)
Then the response status code is 201
And the response body conforms to schema schema/project-instance-schema.json

Scenario: Project - POST new project with non URL friendly name results in 400 error
Given using credentials admin:secret
When project Invalid Project Name is created (POST)
Then the response status code is 400
And the response body conforms to schema schema/error-schema.json

