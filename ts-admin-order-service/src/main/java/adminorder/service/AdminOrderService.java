package adminorder.service;

import adminorder.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;


/**
 * @author fdse
 */
public interface AdminOrderService {
    Response getAllOrders(HttpHeaders headers);

    Response deleteOrder(  String orderId,String trainNumber, HttpHeaders headers);
    Response updateOrder(Order request, HttpHeaders headers);
    Response addOrder(Order request, HttpHeaders headers);
}
