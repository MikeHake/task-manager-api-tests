Scenario:Members - GET member list conforms to schema
Given using credentials admin:secret
And project TestProjectMembers is recreated with members user1,user2 and admins projectAdmin1,projectAdmin2
When members list is retrieved for project TestProjectMembers
Then the response status code is 200
And the response body conforms to schema schema/project-members-schema.json
And project TestProjectMembers contains 4 members