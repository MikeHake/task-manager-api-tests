package steplibrary;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import model.Credentials;
import net.thucydides.core.Thucydides;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

public class BaseSteps extends ScenarioSteps {
    private static final long serialVersionUID = 1L;

    protected static final String STORED_CREDENTIALS = "creds";
    protected static final String STORED_RESPONSE = "response";
    
    static {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 8080;
        RestAssured.basePath = "jee-example-task-app/v1";
    }
    
    /**
     * Stores an object for subsequent steps.
     * @param key
     * @param value
     * @return the object previously at key, or null
     */
    protected Object store(String key, Object value) {
        return Thucydides.getCurrentSession().put(key, value);
    }
    
    /**
     * Retrieves a stored object from a previous step.
     * @param key
     * @return
     */
    protected Object retrieve(String key) {
        return Thucydides.getCurrentSession().get(key);
    } 
    
    /**
     * Convenience method since we do this so often
     * @return stored credentials
     */
    protected Credentials getCurrentCredentials(){
        Credentials creds = (Credentials)retrieve(STORED_CREDENTIALS);
        if(creds==null){
            throw new RuntimeException("Error, attempt to get saved credentials when none have been saved");
        }
        return creds;
    }
    
   /**
    * Convenience method since we do this so often
    * @return stored credentials
    */
   protected void setCurrentCredentials(Credentials creds){
       store(STORED_CREDENTIALS,creds);
   }
    
    /**
     * Convenience method since we do this so often
     * @return last saved Response
     */
    protected Response getLastResponse(){
        Response response = (Response) retrieve(STORED_RESPONSE);
        if(response==null){
            throw new RuntimeException("Error, attempt to get last Response when non has been saved");
        }
        return response;
    }
    
    /**
     * Convenience method since we do this so often
     * @return stored credentials
     */
    protected void setLastResponse(Response response){
        store(STORED_RESPONSE,response);
    }
    
    protected RequestSpecification createRequestSpecificationWithCurrentCredentials(){
        return createRequestSpecification(getCurrentCredentials());
    }
    
    protected RequestSpecification createRequestSpecification(Credentials credentials) {
        RequestSpecification rs = given()
                .auth().preemptive().basic(credentials.getUsername(), credentials.getPassword())
                .relaxedHTTPSValidation()
                .contentType("application/json");
        return rs;
    }
    
    /**
     * Use REST Assured to make API call then cache the Response Object
     * @param path
     * @param args
     * @return
     */
    @Step
    public Response doGet(String path, Object...args){
        RequestSpecification request = createRequestSpecificationWithCurrentCredentials();
        Response response = request.get(path,args);
        setLastResponse(response);
        return response;
    }
    
    /**
     * Use REST Assured to make API call then cache the Response Object
     * @param body
     * @param path
     * @param args
     * @return
     */
    @Step
    public Response doPost(Object body, String path, Object...args){
        RequestSpecification request = createRequestSpecificationWithCurrentCredentials();
        if(body!=null){
            request.body(body);
        }
        Response response = request.post(path,args);
        setLastResponse(response);
        return response;
    }
    
    /**
     * Use REST Assured to make API call then cache the Response Object
     * @param body
     * @param path
     * @param args
     * @return
     */
    @Step
    public Response doPut(Object body, String path, Object...args){
        RequestSpecification request = createRequestSpecificationWithCurrentCredentials();
        if(body!=null){
            request.body(body);
        }
        Response response = request.put(path,args);
        setLastResponse(response);
        return response;
    }
    
    /**
     * Use REST Assured to make API call then cache the Response Object
     * @param path
     * @param args
     * @return
     */
    @Step
    public Response doDelete(String path, Object...args){
        RequestSpecification request = createRequestSpecificationWithCurrentCredentials();
        Response response = request.delete(path,args);
        setLastResponse(response);
        return response;
    }
}
