package ts.trainticket.domain;

public class CancelOrderInfo {

    private String orderId;

    public CancelOrderInfo() {
        //Default Constructor
    }

    public CancelOrderInfo(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
}
