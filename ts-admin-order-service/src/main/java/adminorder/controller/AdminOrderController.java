package adminorder.controller;

import adminorder.domain.request.AddOrderRequest;
import adminorder.domain.request.DeleteOrderRequest;
import adminorder.domain.request.UpdateOrderRequest;
import adminorder.domain.response.AddOrderResult;
import adminorder.domain.response.DeleteOrderResult;
import adminorder.domain.response.GetAllOrderResult;
import adminorder.domain.response.UpdateOrderResult;
import adminorder.service.AdminOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminOrderController {

    @Autowired
    AdminOrderService adminOrderService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminorder/findAll/{id}", method = RequestMethod.GET)
    public GetAllOrderResult getAllOrders(@PathVariable String id){
        return adminOrderService.getAllOrders(id);
    }

    @RequestMapping(value = "/adminorder/addOrder", method= RequestMethod.POST)
    public AddOrderResult addOrder(@RequestBody AddOrderRequest request){
        return adminOrderService.addOrder(request);
    }

    @RequestMapping(value = "/adminorder/updateOrder", method= RequestMethod.POST)
    public UpdateOrderResult updateOrder(@RequestBody UpdateOrderRequest request){
        return adminOrderService.updateOrder(request);
    }

    @RequestMapping(value = "/adminorder/deleteOrder", method= RequestMethod.POST)
    public DeleteOrderResult deleteOrder(@RequestBody DeleteOrderRequest request){
        return adminOrderService.deleteOrder(request);
    }

}
