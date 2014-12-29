package jbehave.steps;

import java.util.List;

import model.ProjectCollection;
import model.ProjectTeamMemberCollection;
import model.Project;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import service.ProjectMemberService;
import service.ProjectService;
import utilities.ProjectUtils;

import com.jayway.restassured.response.Response;

public class ProjectSteps extends BaseSteps {
    private ProjectService projectService = new ProjectService();
    private ProjectMemberService projectMemberService = new ProjectMemberService();
    private ProjectUtils projectUtils = new ProjectUtils(projectService);

    @When("project $name is created (POST)")
    public void whenPostProject(@Named("name") String name) {
        Project project = new Project(name,name, "Test description");
        Response response = projectService.postProject(project, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @When("project $name is retrieved (GET)")
    public void whenGetProject(@Named("name") String name) {
        Response response = projectService.getProjectInstance(name, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @When("the list of all projects is retrieved (GET)")
    public void whenGetProjectList() {
        Response response = projectService.getProjectCollection(getCurrentCredentials());
        setLastResponse(response);
    }
    
    @When("project $name is deleted")
    public void whenDeleteProject(@Named("name") String name) {
        Response response = projectService.deleteProject(name,getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Given("the project $name does not exist")
    public void givenProjectDoesNotExist(@Named("name") String name) {
        projectUtils.ensureProjectDoesNotExist(name, getCurrentCredentials());
    }
    
    @Then("project $name is not found")
    public void thenProjectIsNotFound(@Named("name") String name) {
        Response response = projectService.getProjectInstance(name, getCurrentCredentials());
        // We expect it to not be found
        response.then().assertThat().statusCode(404);
    }
    
    @Given("the project named $name exists")
    public void givenProjectExists(@Named("name") String name) {
        projectUtils.ensureProjectExists(name, getCurrentCredentials());
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
    
    @Given("project $projectName is recreated in default state")
    public void givenRecreateProject(@Named("projectName") String projectName) {
        projectUtils.recreateProject(projectName, getCurrentCredentials());
    }
    
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
    
    @When("the display name of $projectName is updated via PUT to $displayName")
    public void whenDisplayNameChanged(@Named("projectName") String projectName,@Named("displayName") String displayName) {
        Response response = projectService.getProjectInstance(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        Project project = response.as(Project.class);
        project.setDisplayName(displayName);
        response = projectService.putProject(projectName,project,getCurrentCredentials());
        setLastResponse(response);
    }
    
    @When("the description of $projectName is updated via PUT to $description")
    public void whenDescriptionChanged(@Named("projectName") String projectName,@Named("description") String description) {
        Response response = projectService.getProjectInstance(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        Project project = response.as(Project.class);
        project.setDescription(description);
        response = projectService.putProject(projectName,project,getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Then("project $projectName has a display name of $displayName")
    public void thenVerifyDisplayName(@Named("projectName") String projectName,@Named("displayName") String displayName) {
        Response response = projectService.getProjectInstance(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        Project project = response.as(Project.class);
        Assert.assertEquals("Incorrect display name", displayName, project.getDisplayName());
    }
    
    @Then("project $projectName has a description of $description")
    public void thenVerifyDescription(@Named("projectName") String projectName,@Named("description") String description) {
        Response response = projectService.getProjectInstance(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        Project project = response.as(Project.class);
        Assert.assertEquals("Incorrect display name", description, project.getDescription());
    }
    
    @Then("the response project list contains project $projectName")
    public void thenVerifyProjectListContains(@Named("projectName") String projectName) {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        ProjectCollection collection = response.as(ProjectCollection.class);
        Assert.assertTrue("Expected project is missing from list: "+projectName, collection.isProjectPresent(projectName));
    }
    
    @Then("the response project list does not contain project $projectName")
    public void thenVerifyProjectListDoesNotContain(@Named("projectName") String projectName) {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        ProjectCollection collection = response.as(ProjectCollection.class);
        Assert.assertFalse("Unexpected project in the list: "+projectName, collection.isProjectPresent(projectName));
    }
    
    @Then("the items in the returned project collection each has a URL to fetch that project instance")
    public void thenVerifyProjectColectionURLs() {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        ProjectCollection collection = response.as(ProjectCollection.class);
        for(Project project : collection.getItems()){
            String url = project.getUrl();
            Assert.assertTrue(url.endsWith("projects/"+project.getName()));
        }
    }
    
}
