package steplibrary;

import static com.jayway.restassured.RestAssured.given;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.Response;
import com.jayway.restassured.specification.RequestSpecification;

import model.Credentials;
import net.thucydides.core.Thucydides;
import net.thucydides.core.annotations.Step;
import net.thucydides.core.steps.ScenarioSteps;

/**
 * This extends the Thucydides ScenarioSteps and provides some methods 
 * for interacting with both Thucydides and RestAssured.
 * 
 * All Step Library classes will extend this
 */
public class ThucydidesRestAssuredTestSteps extends ScenarioSteps {
    private static final long serialVersionUID = 1L;

    public static final String STORED_CREDENTIALS = "creds";
    public static final String STORED_RESPONSE = "response";
    
    /**
     * The relative URL paths that will be used in the REST Assured
     * calls to get, post, put, delete.
     */
    public static final String PROJECT_INSTANCE_URL     = "/projects/{name}";
    public static final String PROJECT_COLLECTION_URL   = "/projects";
    public static final String MEMBER_INSTANCE_URL      = "/projects/{projectName}/members/{memberName}";
    public static final String MEMBER_COLLECTION_URL    = "/projects/{projectName}/members";
    public static final String ADMIN_INSTANCE_URL       = "/projects/{projectName}/admins/{memberName}";
    public static final String ADMIN_COLLECTION_URL     = "/projects/{projectName}/admins";
    public static final String TASK_INSTANCE_URL        = "/projects/{name}/tasks/{id}";
    public static final String TASK_COLLECTION_URL      = "/projects/{name}/tasks";
    
    /**
     * Static initializer block to configure the base URL that RestAssured
     * will use. I dont like this solution as I would prefer to set this in
     * a config file or pass it at the command line. However I have not been
     * able to identify any built in functionality in Rest Assured, JBehave,
     * or Thucydides that allows setting user defined properties. 
     * 
     * If it were important to me to be able to execute these tests against
     * different target systems I would define my own properties file for these
     * and implement logic to first look for environment variables, and then
     * fall back to the properties file. However at the moment that is not 
     * important to me so I will just hard code here.
     */
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
            throw new RuntimeException("Error, attempt to get last Response when none has been saved");
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
    
    /**
     * Create a Rest Assured RequestSpecification using the currently saved credentials
     */
    protected RequestSpecification createRequestSpecificationWithCurrentCredentials(){
        return createRequestSpecification(getCurrentCredentials());
    }
    
    /**
     * Create a Rest Assured RequestSpecification using the supplied Credentials
     */
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
