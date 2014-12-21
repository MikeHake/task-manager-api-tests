Scenario:Tasks - GET single task instance by task id
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And task my task 1:task description 1 is added to TestProject1
When the task ID in the previous response is used to GET a single task instance
Then the response status code is 200
And the response body conforms to schema schema/task-instance-schema.json
And the received Task has a title:my task 1 and description:task description 1