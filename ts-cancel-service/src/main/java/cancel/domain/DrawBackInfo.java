package cancel.domain;

/**
 * Created by Administrator on 2017/7/3.
 */
public class DrawBackInfo {

    private String userId;
    private String money;

    public DrawBackInfo(){}

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
