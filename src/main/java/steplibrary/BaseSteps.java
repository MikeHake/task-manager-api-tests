package steplibrary;

import com.jayway.restassured.response.Response;

import model.Credentials;
import net.thucydides.core.Thucydides;
import net.thucydides.core.steps.ScenarioSteps;

public class BaseSteps extends ScenarioSteps {
    private static final long serialVersionUID = 1L;

    protected static final String STORED_CREDENTIALS = "creds";
    protected static final String STORED_RESPONSE = "response";
    
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
}
