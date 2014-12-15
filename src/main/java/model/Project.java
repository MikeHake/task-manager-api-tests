package model;


public class Project extends ApiObject{

    private String name;
    private String displayName;
    private String description;
    private ProjectTeamMembers teamMembers;
    
    public Project() {}
    
    public Project(String name, String displayName, String description) {
        super();
        this.name = name;
        this.displayName = displayName;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ProjectTeamMembers getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(ProjectTeamMembers teamMembers) {
        this.teamMembers = teamMembers;
    }
    
}
