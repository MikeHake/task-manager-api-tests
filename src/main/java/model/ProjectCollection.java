package model;

import java.util.Iterator;
import java.util.List;

public class ProjectCollection extends ApiObject{

    private List<Project> items;

    public List<Project> getItems() {
        return items;
    }

    public void setItems(List<Project> items) {
        this.items = items;
    }
    
    public boolean isProjectPresent(String name){
        Iterator<Project> i = items.iterator();
        while(i.hasNext() ){
            Project p = i.next();
            if(p.getName().equals(name)){
                return true;
            }
        }
        return false;
    }
}
