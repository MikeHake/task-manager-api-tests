package jbehave.stepdefinitions;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import steplibrary.CommonSteps;

public class CommonStepDefinitions {

    // Step library classes injected by Thucydides
    @Steps
    CommonSteps commonSteps;

    @Given("using credentials $user:$password")
    @When("using credentials $user:$password")
    @Then("using credentials $user:$password")
    public void usingCredentials(@Named("user") String user, @Named("password") String password) {
        commonSteps.setCredentials(user, password);
    }

    @Then("the response status code is $status")
    public void thenResponseStatusIs(@Named("status") int status) {
        commonSteps.verifyLastResponseStatus(status);
    }

    /**
     * Check both the status code and the structure of the response body
     * 
     * @param status
     * @param schema
     */
    @Then("the response status is $status and the response body conforms to schema $schema")
    public void thenVerifyResponseCodeAndSchema(@Named("status") int status, @Named("schema") String schema) {
        commonSteps.verifyLastResponseStatus(status);
        commonSteps.verifyLastResponseBodySchema(schema);
    }
}
