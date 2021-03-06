Scenario: Project - POST new project results in 201 and project in body
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is created (POST)
Then the response status is 201 and the response body conforms to schema schema/project-instance-schema.json

Scenario: Project - POST new project with non URL friendly name results in 400 error
Given using credentials admin:secret
When project Invalid Project Name is created (POST)
Then the response status is 400 and the response body conforms to schema schema/error-schema.json

Scenario: Project - POST new project contains URL to fetch
Given using credentials admin:secret
And the project TestProject5 does not exist
When project TestProject5 is created (POST)
Then the returned project instance has a URL to fetch that project instance
