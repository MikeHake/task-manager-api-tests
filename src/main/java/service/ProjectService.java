package service;

import static com.jayway.restassured.RestAssured.given;

import model.Constants;
import model.Credentials;
import model.Project;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class ProjectService {
    
    private String apiUrl;

    public ProjectService(){
        this.apiUrl = Constants.PROJECT_URL;
    }
    
    /**
     * GET /project/{name}
     */
    public Response getProject(String name, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.get(apiUrl+name);
        return response;
    }
    
    /**
     * GET /project/
     */
    public Response getProjectList(Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.get(apiUrl);
        return response;
    }
    
    /**
     * POST /project/{name}
     */
    public Response postProject(String name, Project project, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        request.body(project);
        Response response = request.post(apiUrl+name);
        return response;
    }
    
    /**
     * PUT /project/{name}
     */
    public Response putProject(String name, Project project, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        request.body(project);
        Response response = request.put(apiUrl+name);
        return response;
    }
    
    /**
     * DELETE /project/{name}
     */
    public Response deleteProject(String name, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.delete(apiUrl+name);
        return response;
    }
    
    public static RequestSpecification createRequestSpecification(Credentials credentials) {
        RequestSpecification rs = given()
                .auth().preemptive().basic(credentials.getUsername(), credentials.getPassword())
                .relaxedHTTPSValidation()
                .contentType("application/json");
        return rs;
    }
}
