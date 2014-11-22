package model;

/**
 * All objects returned from the API will have a url
 *
 */
public class ApiObject {

    protected String url;
    
    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
