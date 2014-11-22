
!-- NOTE, see if its valid to specify name on create
Scenario: Project - Create new project as global admin
@tags api:\project\{name} POST
Given using credentials admin:secret
When a new project with name NewProject1 is POST to the project API
Then the response status code is 201
And a GET can be performed to retrieve project NewProject1