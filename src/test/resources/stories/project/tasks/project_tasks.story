
Scenario:Tasks - POST new task to project as project member
Given using credentials admin:secret
And project TestProject1 is initialized with members user1
When using credentials user1:secret
And task title1:description1 is added to TestProject1
Then the response status code is 201
And task title1:description1 is present on TestProject1

Scenario:Tasks - POST non project member not allowed to add task
Given this scenario is pending