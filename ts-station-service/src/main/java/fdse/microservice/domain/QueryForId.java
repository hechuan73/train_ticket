package fdse.microservice.domain;

import javax.management.Query;

/**
 * Created by Wenyi on 2017/6/15.
 */
public class QueryForId {

    private String name;

    public QueryForId(){
        //Default Constructor
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
