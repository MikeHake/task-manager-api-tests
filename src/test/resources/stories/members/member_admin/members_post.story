Scenario:Members - POST add member to project as global admin
Given using credentials admin:secret
And project TestProjectMembers is recreated in default state
When user user1 is added to TestProjectMembers
Then the response status code is 204
And user user1 is listed as a member of project TestProjectMembers

Scenario:Members - POST add member to project as project admin
Given using credentials admin:secret
And project TestProjectMembers is recreated with members user1 and admins projectAdmin1,projectAdmin2
And using credentials projectAdmin1:secret
When user user2 is added to TestProjectMembers
Then the response status code is 204
And user user2 is listed as a member of project TestProjectMembers

Scenario:Members - POST add member fails with 403 when user not project admin
Given using credentials admin:secret
And project TestProjectMembers is recreated with members user1 and admins projectAdmin1,projectAdmin2
And using credentials user1:secret
When user user4 is added to TestProjectMembers
Then the response status is 403 and the response body conforms to schema schema/error-schema.json
And project TestProjectMembers contains 3 members