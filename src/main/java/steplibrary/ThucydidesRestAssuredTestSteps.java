package steplibrary;

import static com.jayway.restassured.RestAssured.given;

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
     * The URL paths that will be used in the REST Assured
     * calls to get, post, put, delete.
     */
    public static final String PROJECT_INSTANCE     = "/projects/{name}";
    public static final String PROJECT_COLLECTION   = "/projects";
    public static final String MEMBER_INSTANCE      = "/projects/{projectName}/members/{memberName}";
    public static final String MEMBER_COLLECTION    = "/projects/{projectName}/members";
    public static final String ADMIN_INSTANCE       = "/projects/{projectName}/admins/{memberName}";
    public static final String ADMIN_COLLECTION     = "/projects/{projectName}/admins";
    public static final String TASK_INSTANCE        = "/projects/{name}/tasks/{id}";
    public static final String TASK_COLLECTION      = "/projects/{name}/tasks";
    
    /**
     * These will all get initialized with the full path in the constructor 
     */
    public String BASE_PATH;
    public String PROJECT_INSTANCE_URL;
    public String PROJECT_COLLECTION_URL;
    public String MEMBER_INSTANCE_URL;
    public String MEMBER_COLLECTION_URL;
    public String ADMIN_INSTANCE_URL;
    public String ADMIN_COLLECTION_URL;
    public String TASK_INSTANCE_URL;
    public String TASK_COLLECTION_URL;
    
    public ThucydidesRestAssuredTestSteps(){
        /*
         * Initialize the value for the URL of the test system. A better way to do this would
         * be to read it from a property file or environment variable. But for now just hardcoding
         * it here will work for the purpose of this example. 
         */
        BASE_PATH = "http://localhost:8080/task-manager-jee/v1";
        
        PROJECT_INSTANCE_URL     = BASE_PATH + PROJECT_INSTANCE;
        PROJECT_COLLECTION_URL   = BASE_PATH + PROJECT_COLLECTION;
        MEMBER_INSTANCE_URL      = BASE_PATH + MEMBER_INSTANCE;
        MEMBER_COLLECTION_URL    = BASE_PATH + MEMBER_COLLECTION;
        ADMIN_INSTANCE_URL       = BASE_PATH + ADMIN_INSTANCE;
        ADMIN_COLLECTION_URL     = BASE_PATH + ADMIN_COLLECTION;
        TASK_INSTANCE_URL        = BASE_PATH + TASK_INSTANCE;
        TASK_COLLECTION_URL      = BASE_PATH + TASK_COLLECTION;
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
