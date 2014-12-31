package jbehave.stepdefinitions;

import model.Task;
import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import steplibrary.ProjectTaskSteps;

public class ProjectTaskStepDefinitions {

    // Step library classes injected by Thucydides
    @Steps
    ProjectTaskSteps projectTaskSteps;

    @Given("task $taskTitle:$taskDescription is added to $projectName")
    @When("task $taskTitle:$taskDescription is added to $projectName")
    public void whenAddTaskToProject(@Named("taskTitle") String taskTitle, @Named("taskDescription") String taskDescription,
            @Named("projectName") String projectName) {
        Task task = projectTaskSteps.createTaskObject(taskTitle, taskDescription);
        projectTaskSteps.postTask(task, projectName);
    }

    @Then("task $taskTitle:$taskDescription is present on $projectName")
    public void thenTaskIsPresentOnProject(@Named("taskTitle") String taskTitle, @Named("taskDescription") String taskDescription,
            @Named("projectName") String projectName) {
        projectTaskSteps.verifyTaskIsPresentOnProject(taskTitle, taskDescription, projectName);
    }

    @When("the task ID in the previous response is used to GET a single task instance")
    public void whenParseTaskAndGetById() {
        projectTaskSteps.parseLastTaskResponseAndGetByID();
    }

    @Then("the received Task has a title:$taskTitle and description:$taskDescription")
    public void thenTaskHasTitleAndDescription(@Named("taskTitle") String taskTitle, @Named("taskDescription") String taskDescription) {
        projectTaskSteps.verifyTaskTitleAndDescription(taskTitle, taskDescription);
    }

    @When("GET all tasks for project $projectName")
    public void whenGetTaskList(@Named("projectName") String projectName) {
        projectTaskSteps.getTaskCollection(projectName);
    }

    @Then("the items in the returned task collection each has a URL to fetch that task instance")
    public void thenVerifyTaskColectionURLs() {
        projectTaskSteps.verifyTaskColectionURLs();
    }

    @Then("the returned task instance has a URL to fetch that instance")
    public void thenVerifyTaskInstanceURL() {
        projectTaskSteps.verifyTaskInstanceURL();
    }
}
