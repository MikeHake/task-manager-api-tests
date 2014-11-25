package jbehave.steps;

import model.Task;

import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;
import model.TaskList;

import service.ProjectTaskService;

import com.jayway.restassured.response.Response;

public class TaskSteps extends BaseSteps {

    private ProjectTaskService projectTaskService = new ProjectTaskService();
    
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
        TaskList taskList = response.as(TaskList.class);
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
}
