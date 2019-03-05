package foodsearch.entity;

public class CancelFoodOrderInfo {

    //just cancel by orderId, not by foodOrderId
    private String orderId;


    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
