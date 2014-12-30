package steplibrary;

import static com.jayway.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import model.Credentials;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class CommonSteps extends BaseSteps{
    private static final long serialVersionUID = 1L;

    @Step
    public void setCredentials(String user, String password) {
        Credentials creds = new Credentials(user,password);
        setCurrentCredentials(creds);
    }
    
    @Step
    public void verifyLastResponseStatus(int status) {
        Response response = getLastResponse();
        Assert.assertEquals(status, response.getStatusCode());
    }
    
    @Step
    public void verifyLastResponseBodySchema(String schema) {
        Response response = getLastResponse();
        response.then().assertThat().body(matchesJsonSchemaInClasspath(schema));
    }
}
