Scenario:Members - DELETE member from project as global admin
Given using credentials admin:secret
And project TestProjectMembers is recreated with members user1,user2 and admins projectAdmin1,projectAdmin2
When user user1 is deleted from TestProjectMembers
Then the response status code is 204
And project TestProjectMembers contains 3 members

Scenario:Members - DELETE member from project as project admin
Given using credentials admin:secret
And project TestProjectMembers is recreated with members user1,user2 and admins projectAdmin1,projectAdmin2
And using credentials projectAdmin1:secret
When user user1 is deleted from TestProjectMembers
Then the response status code is 204
And project TestProjectMembers contains 3 members

Scenario:Members - DELETE member fails with 403 when user not project admin
Given using credentials admin:secret
And project TestProjectMembers is recreated with members user1,user2 and admins projectAdmin1,projectAdmin2
And using credentials user1:secret
When user user2 is deleted from TestProjectMembers
Then the response status code is 403
And project TestProjectMembers contains 4 members
And the response body conforms to schema schema/error-schema.json