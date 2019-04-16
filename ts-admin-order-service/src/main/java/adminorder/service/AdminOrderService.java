package adminorder.service;

import adminorder.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.awt.image.RescaleOp;

public interface AdminOrderService {
    Response getAllOrders(String id, HttpHeaders headers);

    Response deleteOrder(String loginid, String orderId,String trainNumber, HttpHeaders headers);
    Response updateOrder(Order request, HttpHeaders headers);
    Response addOrder(Order request, HttpHeaders headers);
}
