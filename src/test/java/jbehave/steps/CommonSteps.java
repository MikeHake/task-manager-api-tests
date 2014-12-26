package jbehave.steps;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import model.Credentials;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class CommonSteps extends BaseSteps {

    @Given("using credentials $user:$password")
    @When("using credentials $user:$password")
    @Then("using credentials $user:$password")
    public void usingCredentials(@Named("user") String user, @Named("password") String password) {
        Credentials creds = new Credentials(user,password);
        setCurrentCredentials(creds);
    }
    
    @Then("the response status code is $status")
    public void thenResponseStatusIs(@Named("status") int status) {
        Response response = getLastResponse();
        Assert.assertEquals(status, response.getStatusCode());
    }
    
    /**
     * Check both the status code and the structure of the response body
     * @param status
     * @param schema
     */
    @Then("the response status is $status and the response body conforms to schema $schema")
    public void thenVerifyResponseCodeAndSchema(@Named("status") int status, @Named("schema") String schema) {
        Response response = getLastResponse();
        Assert.assertEquals(status, response.getStatusCode());
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }
}
