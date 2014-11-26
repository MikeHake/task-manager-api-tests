package service;

import static com.jayway.restassured.RestAssured.given;

import model.Constants;
import model.Credentials;

import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

public class UserService {

    private String apiUrl;

    public UserService(){
        this.apiUrl = Constants.USER_URL;
    }
    
    public static RequestSpecification createRequestSpecification(Credentials credentials) {
        RequestSpecification rs = given()
                .auth().preemptive().basic(credentials.getUsername(), credentials.getPassword())
                .relaxedHTTPSValidation()
                .contentType("application/json");
        return rs;
    }
    
    /**
     * GET /users/
     */
    public Response getUserList(Credentials credentials){
        RequestSpecification request = createRequestSpecification(credentials);
        Response response = request.get(apiUrl);
        return response;
    }
}
