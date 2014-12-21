Scenario: Project - POST new project with non URL friendly name results in 400 error
Given using credentials admin:secret
When project Invalid Project Name is created (POST)
Then the response status code is 400

Scenario: Project - GET invalid project results in 404
Given using credentials admin:secret
And the project TestProject1 does not exist
When project TestProject1 is retrieved (GET)
Then the response status code is 404

Scenario: Project - PUT update project display name as non admin is unauthorized
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials user1:secret
When the display name of TestProject1 is updated via PUT to New Display Name for Project
Then the response status code is 403

Scenario: Project - PUT update project description as non admin is unauthorized
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials user1:secret
When the description of TestProject1 is updated via PUT to New Description for Project
Then the response status code is 403

Scenario: Project - GET single project details as user NOT on project results in 403
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials user2:secret
When project TestProject1 is retrieved (GET)
Then the response status code is 403