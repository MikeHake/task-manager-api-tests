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
    
    @Then("the response body conforms to schema $schema")
    public void thenVerifySchema(@Named("schema") String schema) {
        Response response = getLastResponse();
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }
}
