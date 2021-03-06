Scenario: Tasks - POST new task to project as project member
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
When using credentials user1:secret
And task title1:description1 is added to TestProject1
Then the response status is 201 and the response body conforms to schema schema/task-instance-schema.json
And task title1:description1 is present on TestProject1

Scenario: Tasks - POST non project member not allowed to add task
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
When using credentials user2:secret
And task title1:description2 is added to TestProject1
Then the response status is 403 and the response body conforms to schema schema/error-schema.json

Scenario: Tasks - POST new task to project contains URL to fetch
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
When using credentials user1:secret
And task title1:description1 is added to TestProject1
Then the returned task instance has a URL to fetch that instance