Scenario: Tasks - GET all tasks on a project
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And task title1:description1 is added to TestProject1
And task title2:description2 is added to TestProject1
When using credentials user1:secret
And GET all tasks for project TestProject1
Then the response status is 200 and the response body conforms to schema schema/task-collection-schema.json

Scenario: Tasks - GET task list items contain URL to each instance
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And task title1:description1 is added to TestProject1
And task title2:description2 is added to TestProject1
When using credentials user1:secret
And GET all tasks for project TestProject1
Then the items in the returned task collection each has a URL to fetch that task instance