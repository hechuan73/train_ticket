package adminorder.service;

import adminorder.entity.AddOrderRequest;
import adminorder.entity.DeleteOrderRequest;
import adminorder.entity.UpdateOrderRequest;
import adminorder.entity.AddOrderResult;
import adminorder.entity.DeleteOrderResult;
import adminorder.entity.GetAllOrderResult;
import adminorder.entity.UpdateOrderResult;
import org.springframework.http.HttpHeaders;

public interface AdminOrderService {
    GetAllOrderResult getAllOrders(String id, HttpHeaders headers);
    DeleteOrderResult deleteOrder(DeleteOrderRequest request, HttpHeaders headers);
    UpdateOrderResult updateOrder(UpdateOrderRequest request, HttpHeaders headers);
    AddOrderResult addOrder(AddOrderRequest request, HttpHeaders headers);
}
