package model;

public class ProjectTeamMember extends ApiObject {
    private String id;
    private boolean isProjectAdmin;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public boolean getIsProjectAdmin() {
        return isProjectAdmin;
    }
    public void setIsProjectAdmin(boolean isProjectAdmin) {
        this.isProjectAdmin = isProjectAdmin;
    }
    
    

}
