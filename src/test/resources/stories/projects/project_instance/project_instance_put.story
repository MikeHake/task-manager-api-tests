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
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials projectAdmin1:secret
When the display name of TestProject1 is updated via PUT to New Display Name for Project
Then the response status code is 204
And project TestProject1 has a display name of New Display Name for Project 

Scenario: Project - PUT update project description as project admin
Given using credentials admin:secret
And project TestProject1 is recreated with members user1 and admins projectAdmin1
And using credentials projectAdmin1:secret
When the description of TestProject1 is updated via PUT to New Description for Project
Then the response status code is 204
And project TestProject1 has a description of New Description for Project

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