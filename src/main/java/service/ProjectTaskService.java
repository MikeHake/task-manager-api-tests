package service;

import static com.jayway.restassured.RestAssured.given;

import model.Constants;
import model.Credentials;
import model.Task;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class ProjectTaskService {

private String apiUrl;
    
    public ProjectTaskService(){
        this.apiUrl = Constants.PROJECT_URL;
    }
    
    /**
     * GET /project/{projectName}/task/{id}
     */
    public Response getProjectTask(String projectName, String taskId, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.get(buildURLString(projectName, taskId));
        return response;
    }
    
    /**
     * GET /project/{projectName}/task/
     */
    public Response getProjectTaskList(String projectName, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.get(buildURLString(projectName, null));
        return response;
    }
    
    /**
     * POST /project/{projectName}/task/
     */
    public Response postProjectTask(String projectName, Task task, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        request.body(task);
        Response response = request.post(buildURLString(projectName, null));
        return response;
    }
    
    
    
    /**
     * helper method to create the end url of:
     * /project/{projectName}/task/{memberName}
     */
    private String buildURLString(String projectName, String taskId){
        StringBuilder builder = new StringBuilder(apiUrl);
        builder.append(projectName);
        builder.append("/task/");
        if(taskId!=null){
            builder.append(taskId);
        }
        return builder.toString();
    }
    
    private static RequestSpecification createRequestSpecification(Credentials credentials) {
        RequestSpecification rs = given()
                .auth().preemptive().basic(credentials.getUsername(), credentials.getPassword())
                .relaxedHTTPSValidation()
                .contentType("application/json");
        return rs;
    }
}
