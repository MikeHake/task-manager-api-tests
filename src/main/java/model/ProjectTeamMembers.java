package model;

import java.util.List;

public class ProjectTeamMembers extends ApiObject{

    private List<ProjectTeamMember> items;

    public List<ProjectTeamMember> getItems() {
        return items;
    }

    public void setItems(List<ProjectTeamMember> items) {
        this.items = items;
    }
    
    public boolean isMemberPresent(String memberId){
        for(ProjectTeamMember member : items){
            if(member.getId().equals(memberId)){
                return true;
            }
        }
        return false;
    }
}
