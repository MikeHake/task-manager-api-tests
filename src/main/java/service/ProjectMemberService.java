package service;

import static com.jayway.restassured.RestAssured.given;
import model.Constants;
import model.Credentials;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class ProjectMemberService {

    private String apiUrl;
    
    public ProjectMemberService(){
        this.apiUrl = Constants.PROJECT_URL;
    }
    
    /**
     * PUT /projects/{projectName}/members/{memberName}
     */
    public Response putMemberToProject(String projectName, String memberName, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.put(buildURLString(projectName, memberName));
        return response;
    }
    
    /**
     * POST /projects/{projectName}/members/{memberName}
     */
    public Response postMemberToProject(String projectName, String memberName, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.post(buildURLString(projectName, memberName));
        return response;
    }
    
    /**
     * DELETE /projects/{projectName}/members/{memberName}
     */
    public Response deleteMemberFromProject(String projectName, String memberName, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.delete(buildURLString(projectName, memberName));
        return response;
    }
    
    /**
     * GET /projects/{projectName}/members/
     */
    public Response getAllMembersOnProject(String projectName, Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.get(buildURLString(projectName, null));
        return response;
    }
    
    
    /**
     * helper method to create the end url of:
     * /projects/{projectName}/members/{memberName}
     */
    private String buildURLString(String projectName, String memberName){
        StringBuilder builder = new StringBuilder(apiUrl);
        builder.append(projectName);
        builder.append("/members/");
        if(memberName!=null){
            builder.append(memberName);
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
