Scenario: Project - GET single project details as global admin and verify schema
Given using credentials admin:secret
And the project named TestProject1 exists
When project TestProject1 is retrieved (GET)
Then the response status is 200 and the response body conforms to schema schema/project-instance-schema.json

Scenario: Project - GET single project details as user on project results in success
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials user1:secret
When project TestProject1 is retrieved (GET)
Then the response status is 200 and the response body conforms to schema schema/project-instance-schema.json

Scenario: Project - GET invalid project results in 404
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is retrieved (GET)
Then the response status is 404 and the response body conforms to schema schema/error-schema.json

Scenario: Project - GET single project details as user NOT on project results in 403
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials user2:secret
When project TestProject1 is retrieved (GET)
Then the response status is 403 and the response body conforms to schema schema/error-schema.json

Scenario: Project - GET single project details contains URL to fetch
Given using credentials admin:secret
And the project named TestProject1 exists
When project TestProject1 is retrieved (GET)
Then the returned project instance has a URL to fetch that project instance
