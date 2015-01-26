package steplibrary;

import model.Task;
import model.TaskCollection;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class ProjectTaskSteps extends ThucydidesRestAssuredTestSteps {
    private static final long serialVersionUID = 1L;

    @Step
    public Response postTask(Task task, String projectName) {
        return doPost(task, TASK_COLLECTION_URL, projectName);
    }
    
    @Step
    public Response getTask(String projectName, String taskId) {
        return doGet(TASK_INSTANCE_URL,projectName, taskId);
    }
    
    @Step
    public Response getTaskCollection(String projectName) {
        return doGet(TASK_COLLECTION_URL,projectName);
    }
    
    @Step
    public Task createTaskObject(String taskTitle, String taskDescription){
        Task task = new Task(taskTitle, taskDescription);
        return task;
    }

    @Step
    public void verifyTaskIsPresentOnProject(String taskTitle, String taskDescription, String projectName) {
        // fetch all tasks on project and ensure its there
        Response response = getTaskCollection(projectName);
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
        String taskId = task.getId();
        String projectName = task.getProject();
        getTask(projectName, taskId);
    }

    @Step
    public void verifyTaskTitleAndDescription(String taskTitle, String taskDescription) {
        Response response = getLastResponse();
        Task task = response.as(Task.class);
        Assert.assertEquals(taskTitle, task.getTitle());
        Assert.assertEquals(taskDescription, task.getDescription());
    }

    @Step
    public void verifyTaskColectionURLs() {
        Response response = getLastResponse();
        response.then().assertThat().statusCode(200);
        TaskCollection collection = response.as(TaskCollection.class);
        String projectName = collection.getItems().get(0).getProject();
        Assert.assertEquals("Task collection URL incorrect", BASE_PATH + "/projects/" + projectName + "/tasks", collection.getUrl());
        for (Task task : collection.getItems()) {
            Assert.assertEquals("Task instance URL incorrect", BASE_PATH + "/projects/" + projectName + "/tasks/" + task.getId(),
                    task.getUrl());
        }
    }

    @Step
    public void verifyTaskInstanceURL() {
        Response response = getLastResponse();
        Task instance = response.as(Task.class);
        Assert.assertEquals("Task instance URL incorrect", BASE_PATH + "/projects/" + instance.getProject() + "/tasks/" + instance.getId(),
                instance.getUrl());
    }

}
