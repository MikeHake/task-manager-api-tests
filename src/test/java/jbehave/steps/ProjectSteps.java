package jbehave.steps;

import model.Constants;
import model.Project;
import model.ProjectCollection;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import service.ProjectService;
import utilities.ProjectUtils;

import com.jayway.restassured.response.Response;

public class ProjectSteps extends BaseSteps {
    private ProjectService projectService = new ProjectService();
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
    
    @Given("project $projectName is recreated in default state")
    public void givenRecreateProject(@Named("projectName") String projectName) {
        projectUtils.recreateProject(projectName, getCurrentCredentials());
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
        Assert.assertEquals("Project collection URL incorrect", Constants.BASE_URL+"projects" , collection.getUrl());
        for(Project project : collection.getItems()){
            Assert.assertEquals("Project instance URL incorrect", Constants.BASE_URL+"projects/"+project.getName() , project.getUrl());
        }
    }
    
    @Then("the returned project instance has a URL to fetch that project instance")
    public void thenVerifyProjectInstanceURL() {
        Response response = getLastResponse();
        Project instance = response.as(Project.class);
        Assert.assertEquals("Project instance URL incorrect", Constants.BASE_URL+"projects/"+instance.getName() , instance.getUrl());
    }
    
}
