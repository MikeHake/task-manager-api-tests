package jbehave.stepdefinitions;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import steplibrary.CommonSteps;
import steplibrary.ProjectSteps;

public class ProjectStepDefinitions {
    @Steps
    ProjectSteps projectSteps;
    
    @Steps
    CommonSteps commonSteps;

    @When("project $name is created (POST)")
    public void whenPostProject(@Named("name") String name) {
        projectSteps.postProject(name);
    }

    @When("project $name is retrieved (GET)")
    public void whenGetProject(@Named("name") String name) {
        projectSteps.getProject(name);
    }

    @When("the list of all projects is retrieved (GET)")
    public void whenGetProjectList() {
        projectSteps.getProjectCollection();
    }

    @When("project $name is deleted")
    public void whenDeleteProject(@Named("name") String name) {
        projectSteps.deleteProject(name);
    }

    @Given("the project $name does not exist")
    public void givenProjectDoesNotExist(@Named("name") String name) {
        projectSteps.deleteProjectIfItExists(name);
    }

    @Then("project $name is not found")
    public void thenProjectIsNotFound(@Named("name") String name) {
        projectSteps.getProject(name);
        commonSteps.verifyLastResponseStatus(404);
    }

    @Given("the project named $name exists")
    public void givenProjectExists(@Named("name") String name) {
        projectSteps.createProjectIfDoesNotExist(name);
    }

    @Given("project $projectName is recreated in default state")
    public void givenRecreateProject(@Named("projectName") String projectName) {
        projectSteps.recreateProject(projectName);
    }

    @When("the display name of $projectName is updated via PUT to $displayName")
    public void whenDisplayNameChanged(@Named("projectName") String projectName, @Named("displayName") String displayName) {
        projectSteps.changeProjectDisplayName(projectName, displayName);
    }

    @When("the description of $projectName is updated via PUT to $description")
    public void whenDescriptionChanged(@Named("projectName") String projectName, @Named("description") String description) {
        projectSteps.changeProjectDescription(projectName, description);
    }

    @Then("project $projectName has a display name of $displayName")
    public void thenVerifyDisplayName(@Named("projectName") String projectName, @Named("displayName") String displayName) {
        projectSteps.verifyProjectDisplayName(projectName, displayName);
    }

    @Then("project $projectName has a description of $description")
    public void thenVerifyDescription(@Named("projectName") String projectName, @Named("description") String description) {
        projectSteps.verifyProjectDescription(projectName, description);
    }

    @Then("the response project list contains project $projectName")
    public void thenVerifyProjectListContains(@Named("projectName") String projectName) {
        projectSteps.verifyProjectListContains(projectName);
    }

    @Then("the response project list does not contain project $projectName")
    public void thenVerifyProjectListDoesNotContain(@Named("projectName") String projectName) {
        projectSteps.verifyProjectListDoesNotContain(projectName);
    }

    @Then("the items in the returned project collection each has a URL to fetch that project instance")
    public void thenVerifyProjectColectionURLs() {
        projectSteps.verifyProjectColectionURLs();
    }

    @Then("the returned project instance has a URL to fetch that project instance")
    public void thenVerifyProjectInstanceURL() {
        projectSteps.verifyProjectInstanceURL();
    }

}
