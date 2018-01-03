package rebook.domain;

/**
 * Created by Administrator on 2017/6/20.
 */
public class PaymentInfo {

    private String orderId;

    private String tripId;

    public PaymentInfo(){
        //Default Constructor
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTripId() {
        return tripId;
    }

    public void setTripId(String tripId) {
        this.tripId = tripId;
    }
}
