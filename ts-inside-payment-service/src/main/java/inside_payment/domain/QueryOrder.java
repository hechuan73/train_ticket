package inside_payment.domain;

/**
 * Created by Administrator on 2017/6/20.
 */
public class QueryOrder {

    private String orderId;

    public QueryOrder(){}

    public QueryOrder(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

}
