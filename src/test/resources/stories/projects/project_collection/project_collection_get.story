Scenario: Project - GET project list as global admin and verify schema
Given using credentials admin:secret
And the project named TestProject1 exists
And the project named TestProject2 exists
When the list of all projects is retrieved (GET)
Then the response status is 200 and the response body conforms to schema schema/project-collection-schema.json

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

Scenario: Project - GET project list items contain URL to each instance
Given using credentials admin:secret
And the project named TestProject1 exists
And the project named TestProject2 exists
When the list of all projects is retrieved (GET)
Then the items in the returned project collection each has a URL to fetch that project instance