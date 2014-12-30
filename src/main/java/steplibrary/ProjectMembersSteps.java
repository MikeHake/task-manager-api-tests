package steplibrary;

import java.util.List;

import model.ProjectTeamMemberCollection;
import net.thucydides.core.annotations.Step;

import org.junit.Assert;

import service.ProjectMemberService;
import service.ProjectService;
import utilities.ProjectUtils;

import com.jayway.restassured.response.Response;

public class ProjectMembersSteps extends BaseSteps{
    private static final long serialVersionUID = 1L;

    private ProjectService projectService = new ProjectService();
    private ProjectMemberService projectMemberService = new ProjectMemberService();
    private ProjectUtils projectUtils = new ProjectUtils(projectService);

    @Step
    public void addMemberToProject(String userName,String projectName) {
        Response response = projectMemberService.putMemberToProject(projectName, userName, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Step
    public void deleteMemberFromProject(String userName,String projectName) {
        Response response = projectMemberService.deleteMemberFromProject(projectName, userName, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Step
    public void verifyUserIsMemberOfProject(String userName,String projectName) {
        Response response = projectMemberService.getProjectMemberCollection(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        ProjectTeamMemberCollection membersList = response.as(ProjectTeamMemberCollection.class);
        Assert.assertTrue("User '"+userName+"' not present in project members list", membersList.isMemberPresent(userName));
    }
    
    @Step
    public void getMembersOfProject(String projectName) {
        Response response = projectMemberService.getProjectMemberCollection(projectName, getCurrentCredentials());
        setLastResponse(response);
    }
    
    @Step
    public void verifyNumberOfMembersOnProject(String projectName,int count) {
        Response response = projectMemberService.getProjectMemberCollection(projectName, getCurrentCredentials());
        response.then().assertThat().statusCode(200);
        ProjectTeamMemberCollection membersList = response.as(ProjectTeamMemberCollection.class);
        Assert.assertEquals("Incorrect number of project members", count, membersList.getItems().size());
    }
    
    @Step
    public void recreateAndInitializeProject(String projectName,List<String> userList, List<String> adminList) {
        projectUtils.recreateProject(projectName, getCurrentCredentials());
        
        for(String userName : userList){
            Response response = projectMemberService.putMemberToProject(projectName, userName, getCurrentCredentials());
            response.then().assertThat().statusCode(204);
        }
        
        for(String adminName : adminList){
            Response response = projectMemberService.putAdminToProject(projectName, adminName, getCurrentCredentials());
            response.then().assertThat().statusCode(204);
        }
    }
}
