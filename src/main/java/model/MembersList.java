package model;

import java.util.List;

public class MembersList extends ApiObject{

    private List<String> items;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
