package steplibrary;

import model.Constants;
import model.Project;
import model.ProjectCollection;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import service.ProjectService;
import utilities.ProjectUtils;

import com.jayway.restassured.response.Response;

public class ProjectSteps extends BaseSteps {
    private static final long serialVersionUID = 1L;

    private ProjectService projectService = new ProjectService();
    private ProjectUtils projectUtils = new ProjectUtils(projectService);

    @Step
    public void postProject(String name) {
        Project project = new Project(name, name, "Test description");
        Response response = projectService.postProject(project, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void getProject(String name) {
        Response response = projectService.getProjectInstance(name, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void getProjectCollection() {
        Response response = projectService.getProjectCollection(getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void deleteProject(String name) {
        Response response = projectService.deleteProject(name, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void deleteProjectIfItExists(String name) {
        projectUtils.ensureProjectDoesNotExist(name, getCurrentCredentials());
    }

    @Step
    public void createProjectIfDoesNotExist(String name) {
        projectUtils.ensureProjectExists(name, getCurrentCredentials());
    }

    @Step
    public void recreateProject(String projectName) {
        projectUtils.recreateProject(projectName, getCurrentCredentials());
    }

    @Step
    public void changeProjectDisplayName(String projectName, String displayName) {
        Project project = getProjectInstanceFromAPI(projectName);
        project.setDisplayName(displayName);
        Response response = projectService.putProject(projectName, project, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void changeProjectDescription(String projectName, String description) {
        Project project = getProjectInstanceFromAPI(projectName);
        project.setDescription(description);
        Response response = projectService.putProject(projectName, project, getCurrentCredentials());
        setLastResponse(response);
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
    
    private Project getProjectInstanceFromAPI(String projectName){
        Response response = projectService.getProjectInstance(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        Project project = response.as(Project.class);
        return project;
    }
}
