package jbehave.steps;

import model.Task;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import model.TaskCollection;
import service.ProjectTaskService;

import com.jayway.restassured.response.Response;

public class TaskSteps extends BaseSteps {

    private ProjectTaskService projectTaskService = new ProjectTaskService();
    
    @Given("task $taskTitle:$taskDescription is added to $projectName")
    @When("task $taskTitle:$taskDescription is added to $projectName")
    public void whenAddTaskToProject(@Named("taskTitle") String taskTitle,@Named("taskDescription") String taskDescription,@Named("projectName") String projectName) {
        Task task = new Task(taskTitle, taskDescription);
        Response response = projectTaskService.postProjectTask(projectName, task, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Then("task $taskTitle:$taskDescription is present on $projectName")
    public void thenTaskIsPresentOnProject(@Named("taskTitle") String taskTitle,@Named("taskDescription") String taskDescription,@Named("projectName") String projectName) {
        // fetch all tasks on project and ensure its there
        Response response = projectTaskService.getProjectTaskList(projectName, getCurrentCredentials());
        TaskCollection taskList = response.as(TaskCollection.class);
        Assert.assertTrue("Expected at least 1 task to be present",taskList.getItems().size() > 0);
        boolean found = false;
        for(Task item:taskList.getItems()){
            if(item.getTitle().equals(taskTitle) || item.getDescription().equals(taskDescription)){
                found=true;
                break;
            }
        }
        Assert.assertTrue("Expected task not found in task list", found);
    }
    
    @When("the task ID in the previous response is used to GET a single task instance")
    public void whenParseTaskAndGetById() {
        Response response = getLastResponse();
        Task task = response.as(Task.class);
        String id = task.getId();
        String projectName = task.getProject();
        response = projectTaskService.getProjectTask(projectName, id, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Then("the received Task has a title:$taskTitle and description:$taskDescription")
    public void thenTaskHasTitleAndDescription(@Named("taskTitle") String taskTitle,@Named("taskDescription") String taskDescription) {
        Response response = getLastResponse();
        Task task = response.as(Task.class);
        Assert.assertEquals(taskTitle, task.getTitle());
        Assert.assertEquals(taskDescription, task.getDescription());
    }
}
