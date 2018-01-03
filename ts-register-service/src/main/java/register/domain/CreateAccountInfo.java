package register.domain;

/**
 * Created by Administrator on 2017/6/20.
 */
public class CreateAccountInfo {

    private String userId;

    private String money;

    public CreateAccountInfo(){
        //Default Constructor
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

}
