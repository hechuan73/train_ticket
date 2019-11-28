package adminbasic.entity;

/**
 * @author fdse
 */
public class Config {

    private String name;

    private String value;

    private String description;



    public Config() {
        //Default Constructor
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
