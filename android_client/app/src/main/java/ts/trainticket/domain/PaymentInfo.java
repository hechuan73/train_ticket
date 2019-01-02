package ts.trainticket.domain;


public class PaymentInfo {
    public PaymentInfo() {
    }

    private String orderId;
    private String tripId;

    public PaymentInfo(String orderId, String tripId) {
        this.orderId = orderId;
        this.tripId = tripId;
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
