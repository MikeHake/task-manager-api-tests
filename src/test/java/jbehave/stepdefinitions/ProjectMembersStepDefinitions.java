package jbehave.stepdefinitions;

import java.util.List;

import net.thucydides.core.annotations.Steps;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;

import steplibrary.ProjectMembersSteps;

public class ProjectMembersStepDefinitions {
    
    @Steps
    ProjectMembersSteps projectMembersSteps;

    @When("user $userName is added to $projectName")
    public void whenAddMemberToProject(@Named("userName") String userName,@Named("projectName") String projectName) {
        projectMembersSteps.addMemberToProject(userName, projectName);
    }
    
    @When("user $userName is deleted from $projectName")
    public void whenDeleteMemberFromProject(@Named("userName") String userName,@Named("projectName") String projectName) {
        projectMembersSteps.deleteMemberFromProject(userName, projectName);
    }
    
    @Then("user $userName is listed as a member of project $projectName")
    public void thenUserIsMemberOfProject(@Named("userName") String userName,@Named("projectName") String projectName) {
        projectMembersSteps.verifyUserIsMemberOfProject(userName, projectName);
    }
    
    @When("members list is retrieved for project $projectName")
    public void whenMembersListRetrieved(@Named("projectName") String projectName) {
        projectMembersSteps.getMembersOfProject(projectName);
    }
    
    @Then("project $projectName contains $count members")
    public void thenProjectContainsCountMembers(@Named("projectName") String projectName,@Named("count") int count) {
        projectMembersSteps.verifyNumberOfMembersOnProject(projectName, count);
    }
    
    @Given("project $projectName is recreated with members $userList and admins $adminList")
    public void givenProjectCreatedAndInitialized(@Named("projectName") String projectName,@Named("userList") List<String> userList,
            @Named("adminList") List<String> adminList) {
        projectMembersSteps.recreateAndInitializeProject(projectName, userList, adminList);
    }
}
