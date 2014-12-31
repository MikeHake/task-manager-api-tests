package steplibrary;

import java.util.List;

import model.ProjectTeamMemberCollection;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import com.jayway.restassured.response.Response;

public class ProjectMembersSteps extends ThucydidesRestAssuredTestSteps{
    private static final long serialVersionUID = 1L;

    @Step
    public Response putMember(String userName,String projectName) {
        return doPut(null, MEMBER_INSTANCE_URL, projectName, userName);
    }
    
    @Step
    public Response putAdmin(String userName,String projectName) {
        return doPut(null, ADMIN_INSTANCE_URL, projectName, userName);
    }
    
    @Step
    public Response deleteMember(String userName,String projectName) {
        return doDelete(MEMBER_INSTANCE_URL, projectName, userName);
    }
    
    @Step
    public Response getMemberCollection(String projectName) {
        return doGet(MEMBER_COLLECTION_URL, projectName);
    }
    
    @Step
    public void verifyUserIsMemberOfProject(String userName,String projectName) {
        Response response = getMemberCollection(projectName);
        response.then().assertThat().statusCode(200);
        ProjectTeamMemberCollection membersList = response.as(ProjectTeamMemberCollection.class);
        Assert.assertTrue("User '"+userName+"' not present in project members list", membersList.isMemberPresent(userName));
    }
    
    @Step
    public void verifyNumberOfMembersOnProject(String projectName,int count) {
        Response response = getMemberCollection(projectName);
        response.then().assertThat().statusCode(200);
        ProjectTeamMemberCollection membersList = response.as(ProjectTeamMemberCollection.class);
        Assert.assertEquals("Incorrect number of project members", count, membersList.getItems().size());
    }
    
    @Step
    public void addUsersToProject(String projectName,List<String> userList, List<String> adminList) {
        for(String userName : userList){
            Response response = putMember(userName, projectName);
            response.then().assertThat().statusCode(204);
        }
        
        for(String adminName : adminList){
            Response response =  putAdmin(adminName, projectName);
            response.then().assertThat().statusCode(204);
        }
    }
    
}
