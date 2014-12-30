package steplibrary;

import model.Constants;
import model.Task;
import model.TaskCollection;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import service.ProjectTaskService;

import com.jayway.restassured.response.Response;

public class ProjectTaskSteps extends BaseSteps {
    private static final long serialVersionUID = 1L;

    private ProjectTaskService projectTaskService = new ProjectTaskService();

    @Step
    public void addTaskToProject(String taskTitle, String taskDescription, String projectName) {
        Task task = new Task(taskTitle, taskDescription);
        Response response = projectTaskService.postTask(projectName, task, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void verifyTaskIsPresentOnProject(String taskTitle, String taskDescription, String projectName) {
        // fetch all tasks on project and ensure its there
        Response response = projectTaskService.getTaskCollection(projectName, getCurrentCredentials());
        TaskCollection taskList = response.as(TaskCollection.class);
        Assert.assertTrue("Expected at least 1 task to be present", taskList.getItems().size() > 0);
        boolean found = false;
        for (Task item : taskList.getItems()) {
            if (item.getTitle().equals(taskTitle) || item.getDescription().equals(taskDescription)) {
                found = true;
                break;
            }
        }
        Assert.assertTrue("Expected task not found in task list", found);
    }

    @Step
    public void parseLastTaskResponseAndGetByID() {
        Response response = getLastResponse();
        Task task = response.as(Task.class);
        String id = task.getId();
        String projectName = task.getProject();
        response = projectTaskService.getTaskInstance(projectName, id, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void verifyTaskTitleAndDescription(String taskTitle, String taskDescription) {
        Response response = getLastResponse();
        Task task = response.as(Task.class);
        Assert.assertEquals(taskTitle, task.getTitle());
        Assert.assertEquals(taskDescription, task.getDescription());
    }

    @Step
    public void getTaskCollection(String projectName) {
        Response response = projectTaskService.getTaskCollection(projectName, getCurrentCredentials());
        setLastResponse(response);
    }

    @Step
    public void verifyTaskColectionURLs() {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        TaskCollection collection = response.as(TaskCollection.class);
        String projectName = collection.getItems().get(0).getProject();
        Assert.assertEquals("Task collection URL incorrect", Constants.BASE_URL + "projects/" + projectName + "/tasks", collection.getUrl());
        for (Task task : collection.getItems()) {
            Assert.assertEquals("Task instance URL incorrect", Constants.BASE_URL + "projects/" + projectName + "/tasks/" + task.getId(),
                    task.getUrl());
        }
    }

    @Step
    public void verifyTaskInstanceURL() {
        Response response = getLastResponse();
        Task instance = response.as(Task.class);
        Assert.assertEquals("Task instance URL incorrect", Constants.BASE_URL + "projects/" + instance.getProject() + "/tasks/" + instance.getId(),
                instance.getUrl());
    }

}
