package inside_payment.entity;


public class CreateAccountInfo {

    private String userId;



    private String money;

    public CreateAccountInfo(){}

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
