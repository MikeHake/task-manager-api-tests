package jbehave.steps;

import model.Credentials;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class CommonSteps extends BaseSteps {

    @Given("using credentials $user:$password")
    public void usingCredentials(@Named("user") String user, @Named("password") String password) {
        Credentials creds = new Credentials(user,password);
        store(STORED_CREDENTIALS,creds);
    }
    
    @Then("the response status code is $status")
    public void thenResponseStatusIs(@Named("status") int status) {
        Response response = (Response) retrieve(STORED_RESPONSE);
        Assert.assertEquals(status, response.getStatusCode());
    }
}
