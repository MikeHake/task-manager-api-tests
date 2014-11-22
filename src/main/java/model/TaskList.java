package model;

import java.util.List;

public class TaskList extends ApiObject{

    private List<Task> items;

    public List<Task> getItems() {
        return items;
    }

    public void setItems(List<Task> items) {
        this.items = items;
    }
    
}
