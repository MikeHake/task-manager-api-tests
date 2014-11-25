package jbehave.steps;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.util.List;

import model.Project;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

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
        Response response = projectService.postProject(name, project, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @When("project $name is deleted")
    public void whenDeleteProject(@Named("name") String name) {
        Response response = projectService.deleteProject(name,getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Then("a GET can be performed to retrieve project $name")
    public void thenVerifyProject(@Named("name") String name) {
        Response response = projectService.getProject(name, getCurrentCredentials());
        setLastResponse(response);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("JsonSchema/project-schema.json"));
    }
    
    @Given("the project $name does not exist")
    public void givenProjectDoesNotExist(@Named("name") String name) {
        projectUtils.ensureProjectDoesNotExist(name, getCurrentCredentials());
    }
    
    @Then("project $name is not found")
    public void thenProjectIsNotFound(@Named("name") String name) {
        Response response = projectService.getProject(name, getCurrentCredentials());
        // We expect it to not be found
        response.then().assertThat().statusCode(404);
    }
    
    @Given("the project named $name exists")
    public void givenProjectExists(@Named("name") String name) {
        projectUtils.ensureProjectExists(name, getCurrentCredentials());
    }
    
    @Given("project $projectName is initialized with admins $adminList and users $userList")
    public void givenProjectCreatedAndInitialized(@Named("projectName") String projectName,@Named("adminList") List<String> adminList,@Named("userList") List<String> userList) {
        projectUtils.recreateProject(projectName, getCurrentCredentials());
        for(String userName : adminList){
            Response response = projectMemberService.postMemberToProject(projectName, userName, getCurrentCredentials());
            response.then().assertThat().statusCode(204);
        }
        
        for(String userName : userList){
            Response response = projectMemberService.postMemberToProject(projectName, userName, getCurrentCredentials());
            response.then().assertThat().statusCode(204);
        }
    }
    
    
}
