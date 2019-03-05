package adminorder.entity;

public class AddOrderRequest {
    private String loginid;
    private Order order;

    public AddOrderRequest(){

    }

    public String getLoginid() {
        return loginid;
    }

    public void setLoginid(String loginid) {
        this.loginid = loginid;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
