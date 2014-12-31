package steplibrary;

import model.Constants;
import model.Project;
import model.ProjectCollection;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class ProjectSteps extends ThucydidesRestAssuredTestSteps {
    private static final long serialVersionUID = 1L;
    
    @Step
    public Response postProject(Project project) {
        return doPost(project, PROJECT_COLLECTION_URL);
    }
    
    @Step
    public Response putProject(Project project) {
        return doPut(project, PROJECT_INSTANCE_URL,project.getName());
    }

    @Step
    public Response getProject(String name) {
        return doGet(PROJECT_INSTANCE_URL,name);
    }

    @Step
    public Response getProjectCollection() {
        return doGet(PROJECT_COLLECTION_URL);
    }

    @Step
    public Response deleteProject(String name) {
        return doDelete(PROJECT_INSTANCE_URL,name);
    }

    @Step
    public void createProjectIfDoesNotExist(String name) {
        Response response =  getProject(name);
        if(response.getStatusCode()!=200){
            // project does not exist, must create
            createProject(name);
        }
    }
    
    private void createProject(String name){
        Project project = new Project(name,name, "Test description");
        Response response = postProject(project);
        if(response.statusCode()!=201){
            // something has gone wrong.
            // Calling code should not try to recover so throw unchecked exception
            throw new RuntimeException("Error creating project '"+name+"'");
        }
    }

    @Step
    public void recreateProject(String name) {
        deleteProject(name);
        createProject(name);
    }
    
    @Step
    public void changeProjectDisplayName(String projectName, String displayName) {
        Project project = getProjectInstanceFromAPI(projectName);
        project.setDisplayName(displayName);
        putProject(project);
    }

    @Step
    public void changeProjectDescription(String projectName, String description) {
        Project project = getProjectInstanceFromAPI(projectName);
        project.setDescription(description);
        putProject(project);
    }

    @Step
    public void verifyProjectDisplayName(String projectName, String displayName) {
        Project project = getProjectInstanceFromAPI(projectName);
        Assert.assertEquals("Incorrect display name", displayName, project.getDisplayName());
    }

    @Step
    public void verifyProjectDescription(String projectName, String description) {
        Project project = getProjectInstanceFromAPI(projectName);
        Assert.assertEquals("Incorrect display name", description, project.getDescription());
    }

    @Step
    public void verifyProjectListContains(String projectName) {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        ProjectCollection collection = response.as(ProjectCollection.class);
        Assert.assertTrue("Expected project is missing from list: " + projectName, collection.isProjectPresent(projectName));
    }

    @Step
    public void verifyProjectListDoesNotContain(String projectName) {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        ProjectCollection collection = response.as(ProjectCollection.class);
        Assert.assertFalse("Unexpected project in the list: " + projectName, collection.isProjectPresent(projectName));
    }

    @Step
    public void verifyProjectColectionURLs() {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        ProjectCollection collection = response.as(ProjectCollection.class);
        Assert.assertEquals("Project collection URL incorrect", Constants.BASE_URL + "projects", collection.getUrl());
        for (Project project : collection.getItems()) {
            Assert.assertEquals("Project instance URL incorrect", Constants.BASE_URL + "projects/" + project.getName(), project.getUrl());
        }
    }

    @Step
    public void verifyProjectInstanceURL() {
        Response response = getLastResponse();
        Project instance = response.as(Project.class);
        Assert.assertEquals("Project instance URL incorrect", Constants.BASE_URL + "projects/" + instance.getName(), instance.getUrl());
    }
    
    @Step
    public Project createProjectObject(String name, String displayName, String description){
        Project project = new Project(name, displayName, description);
        return project;
    }
    
    /**
     * Make API call and convert the Response to a Project Object.
     * This is useful when verifying test results
     */
    private Project getProjectInstanceFromAPI(String name){
        Response response = getProject(name);
        response.then().assertThat().statusCode(200);
        Project project = response.as(Project.class);
        return project;
    }
}
