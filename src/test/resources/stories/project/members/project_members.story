
Scenario:Members - POST add member to project as global admin
Given using credentials admin:secret
And project TestProjectMembers is recreated in default state
When user user1 is added to TestProjectMembers
Then the response status code is 204
And user user1 is listed as a member of project TestProjectMembers

Scenario:Members - GET member list conforms to schema
Given using credentials admin:secret
And project TestProjectMembers is initialized with members projectAdmin1,projectAdmin2,user1,user2
When members list is retrieved for project TestProjectMembers
Then the response status code is 200
And the response body conforms to schema schema/project-members-schema.json
And project TestProjectMembers contains 4 members

Scenario:Members - POST add member to project as project admin
Given using credentials admin:secret
And project TestProjectMembers is initialized with members projectAdmin1
And using credentials projectAdmin1:secret
When user user1 is added to TestProjectMembers
Then the response status code is 204
And user user1 is listed as a member of project TestProjectMembers

Scenario:Members - DELETE member from project as global admin
Given using credentials admin:secret
And project TestProjectMembers is initialized with members user1
When user user1 is deleted from TestProjectMembers
Then the response status code is 204
And project TestProjectMembers contains 0 members

Scenario:Members - DELETE member from project as project admin
Given using credentials admin:secret
And project TestProjectMembers is initialized with members user1,projectAdmin1
And using credentials projectAdmin1:secret
When user user1 is deleted from TestProjectMembers
Then the response status code is 204
And project TestProjectMembers contains 1 members

Scenario:Members - POST add member fails with 401 user not project admin
Given using credentials admin:secret
And project TestProjectMembers is initialized with members user1
And using credentials user1:secret
When user user4 is added to TestProjectMembers
Then the response status code is 401
And project TestProjectMembers contains 1 members

Scenario:Members - DELETE member fails with 401 user not project admin
Given using credentials admin:secret
And project TestProjectMembers is initialized with members user1,user2
And using credentials user1:secret
When user user2 is deleted from TestProjectMembers
Then the response status code is 401
And project TestProjectMembers contains 2 members