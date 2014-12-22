package utilities;

import model.Credentials;
import model.Project;
import service.ProjectService;

import com.jayway.restassured.response.Response;

/**
 * Utilities to put the system in the desired state.
 * Will throw unchecked runtime exceptions if unable to 
 * perform the desired operations
 *
 */
public class ProjectUtils {

    private ProjectService projectService;
    
    public ProjectUtils(ProjectService projectService){
        this.projectService = projectService;
    }
    /**
     * Utility method to delete a project if it exists. 
     * @param name
     * @param creds
     */
    public void ensureProjectDoesNotExist(String name,Credentials creds){
        Response response = projectService.deleteProject(name,creds);
        if(response.statusCode()!=204){
            // something has gone wrong.
            // Calling code should not try to recover so throw unchecked exception
            throw new RuntimeException("Error ensuring project '"+name+"' does not exist");
        }
    }
    
    /**
     * Utility method to create a project if it does not exist. 
     * @param name
     * @param creds
     */
    public void ensureProjectExists(String name,Credentials creds){
        Response response = projectService.getProjectInstance(name, creds);
        if(response.getStatusCode()!=200){
            // project does not exist, must create
            createProject(name,creds);
        }
    }
    
    /**
     * Utility to delete and recreate a project to ensure it is in
     * its default state
     * @param name
     * @param creds
     */
    public void recreateProject(String name,Credentials creds){
        ensureProjectDoesNotExist(name,creds);
        createProject(name,creds);
    }
    
    private void createProject(String name,Credentials creds){
        Project project = new Project(name,name, "Test description");
        Response response = projectService.postProject(project, creds);
        if(response.statusCode()!=201){
            // something has gone wrong.
            // Calling code should not try to recover so throw unchecked exception
            throw new RuntimeException("Error creating project '"+name+"'");
        }
    }
    
}
