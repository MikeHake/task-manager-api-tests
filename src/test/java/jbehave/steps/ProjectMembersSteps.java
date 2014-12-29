package jbehave.steps;

import java.util.List;

import model.ProjectTeamMemberCollection;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import service.ProjectMemberService;
import service.ProjectService;
import utilities.ProjectUtils;

import com.jayway.restassured.response.Response;

public class ProjectMembersSteps extends BaseSteps{
    
    private ProjectService projectService = new ProjectService();
    private ProjectMemberService projectMemberService = new ProjectMemberService();
    private ProjectUtils projectUtils = new ProjectUtils(projectService);

    @When("user $userName is added to $projectName")
    public void whenAddMemberToProject(@Named("userName") String userName,@Named("projectName") String projectName) {
        Response response = projectMemberService.putMemberToProject(projectName, userName, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @When("user $userName is deleted from $projectName")
    public void whenDeleteMemberFromProject(@Named("userName") String userName,@Named("projectName") String projectName) {
        Response response = projectMemberService.deleteMemberFromProject(projectName, userName, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Then("user $userName is listed as a member of project $projectName")
    public void thenUserIsMemberOfProject(@Named("userName") String userName,@Named("projectName") String projectName) {
        Response response = projectMemberService.getProjectMemberCollection(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        ProjectTeamMemberCollection membersList = response.as(ProjectTeamMemberCollection.class);
        Assert.assertTrue("User '"+userName+"' not present in project members list", membersList.isMemberPresent(userName));
    }
    
    @When("members list is retrieved for project $projectName")
    public void whenMembersListRetrieved(@Named("projectName") String projectName) {
        Response response = projectMemberService.getProjectMemberCollection(projectName, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Then("project $projectName contains $count members")
    public void thenProjectContainsCountMembers(@Named("projectName") String projectName,@Named("count") int count) {
        Response response = projectMemberService.getProjectMemberCollection(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        ProjectTeamMemberCollection membersList = response.as(ProjectTeamMemberCollection.class);
        Assert.assertEquals("Incorrect number of project members", count, membersList.getItems().size());
    }
    
    @Given("project $projectName is recreated with members $userList and admins $adminList")
    public void givenProjectCreatedAndInitialized(@Named("projectName") String projectName,@Named("userList") List<String> userList,
            @Named("adminList") List<String> adminList) {
        projectUtils.recreateProject(projectName, getCurrentCredentials());
        
        for(String userName : userList){
            Response response = projectMemberService.putMemberToProject(projectName, userName, getCurrentCredentials());
            response.then().assertThat().statusCode(204);
        }
        
        for(String adminName : adminList){
            Response response = projectMemberService.putAdminToProject(projectName, adminName, getCurrentCredentials());
            response.then().assertThat().statusCode(204);
        }
    }
}
