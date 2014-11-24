package jbehave.steps;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import service.ProjectService;
import model.Project;

import com.jayway.restassured.response.Response;

public class ProjectSteps extends BaseSteps {
    private ProjectService projectService = new ProjectService();

    @When("project $name is created (POST)")
    public void whenPostProject(@Named("name") String name) {
        postProject(name,name, "Test description");
    }
    
    @When("project $name is deleted")
    public void whenDeleteProject(@Named("name") String name) {
        deleteProject(name);
    }
    
    @Then("a GET can be performed to retrieve project $name")
    public void thenVerifyProject(@Named("name") String name) {
        getProjectAndVerifySchema(name);
    }
    
    @Given("the project $name does not exist")
    public void givenProjectDoesNotExist(@Named("name") String name) {
        Response response = deleteProject(name);
        response.then().assertThat().statusCode(204);
    }
    
    @Then("project $name is not found")
    public void thenProjectIsNotFound(@Named("name") String name) {
        Response response = projectService.getProject(name, getCurrentCredentials());
        // We expect it to not be found
        response.then().assertThat().statusCode(404);
    }
    
    @Given("the project named $name exists")
    public void givenProjectExists(@Named("name") String name) {
        Response response = projectService.getProject(name, getCurrentCredentials());
        if(response.getStatusCode()!=200){
            // project does not exist, must create
            response = postProject(name,name,"Test Project Description");
            response.then().assertThat().statusCode(201);
        }
    }
    
    private Response getProjectAndVerifySchema(String projectName){
        Response response = projectService.getProject(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("JsonSchema/project-schema.json"));
        return response;
    }
    
    private Response postProject(String name, String displayName, String description){
        Project project = new Project(name,name, "Test description");
        Response response = projectService.postProject(name, project, getCurrentCredentials());
        setLastResponse(response);
        return response;
    }
    
    public Response deleteProject(String name) {
        Response response = projectService.deleteProject(name,getCurrentCredentials());
        setLastResponse(response);
        return response;
    }
}
