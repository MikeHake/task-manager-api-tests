package jbehave.steps;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import model.Credentials;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import service.ProjectService;
import model.Project;

import com.jayway.restassured.response.Response;

public class ProjectSteps extends BaseSteps {
    private ProjectService projectService = new ProjectService();

    @When("a new project with name $name is POST to the project API")
    public void usingCredentials(@Named("name") String name) {
        Credentials creds = (Credentials)retrieve(STORED_CREDENTIALS);
        Response response = createProject(name,name, "Test description" , creds);
        store(STORED_RESPONSE,response);
    }
    
    @Then("a GET can be performed to retrieve project $name")
    public void getAndVerifyProject(@Named("name") String name) {
        Credentials creds = (Credentials)retrieve(STORED_CREDENTIALS);
        getProjectAndVerifySchema(name,creds);
    }
    
    
    private Response createProject(String projectName, String displayName, String description, Credentials credentials){
        Project project = new Project(projectName,displayName, description);
        Response response = projectService.postProject(projectName, project, credentials);
        return response;
    }
    
    private Response getProjectAndVerifySchema(String projectName, Credentials creds){
        Response response = projectService.getProject(projectName, creds);
        response.then().assertThat().statusCode(200);
        response.then().assertThat().body(matchesJsonSchemaInClasspath("JsonSchema/project-schema.json"));
        return response;
    }
}
