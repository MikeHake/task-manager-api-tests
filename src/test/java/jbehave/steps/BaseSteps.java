package jbehave.steps;
import net.thucydides.core.Thucydides;


public class BaseSteps {
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
}
