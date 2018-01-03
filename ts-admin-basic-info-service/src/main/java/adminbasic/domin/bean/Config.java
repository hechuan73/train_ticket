package adminbasic.domin.bean;


/**
 * Created by Chenjie Xu on 2017/5/11.
 */

public class Config {

    private String name;

    private String value;

    private String description;

    private String loginId;

    public Config() {

    }
    public void setName(String name) {
        this.name = name;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
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
