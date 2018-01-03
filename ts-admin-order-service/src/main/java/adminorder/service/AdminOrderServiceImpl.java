package adminorder.service;

import adminorder.domain.bean.DeleteOrderInfo;
import adminorder.domain.bean.Order;
import adminorder.domain.request.AddOrderRequest;
import adminorder.domain.request.DeleteOrderRequest;
import adminorder.domain.request.UpdateOrderRequest;
import adminorder.domain.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public GetAllOrderResult getAllOrders(String id) {
        if(checkId(id)){
            System.out.println("[Admin Order Service][Get All Orders]");
            //Get all of the orders
            ArrayList<Order> orders = new ArrayList<Order>();
            //From ts-order-service
            QueryOrderResult result = restTemplate.getForObject(
                    "http://ts-order-service:12031/order/findAll",
                    QueryOrderResult.class);
            if(result.isStatus()){
                System.out.println("[Admin Order Service][Get Orders From ts-order-service successfully!]");
                orders.addAll(result.getOrders());
            }
            else
                System.out.println("[Admin Order Service][Get Orders From ts-order-service fail!]");
            //From ts-order-other-service
            result = restTemplate.getForObject(
                    "http://ts-order-other-service:12032/orderOther/findAll",
                    QueryOrderResult.class);
            if(result.isStatus()){
                System.out.println("[Admin Order Service][Get Orders From ts-order-other-service successfully!]");
                orders.addAll(result.getOrders());
            }
            else
                System.out.println("[Admin Order Service][Get Orders From ts-order-other-service fail!]");
            //Return orders
            GetAllOrderResult getAllOrderResult = new GetAllOrderResult();
            getAllOrderResult.setStatus(true);
            getAllOrderResult.setMessage("Get the orders successfully!");
            getAllOrderResult.setOrders(orders);
            return getAllOrderResult;
        }
        else{
            System.out.println("[Admin Order Service][Wrong Admin ID]");
            GetAllOrderResult result = new GetAllOrderResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + id);
            return result;
        }
    }

    @Override
    public DeleteOrderResult deleteOrder(DeleteOrderRequest request) {
        if(checkId(request.getLoginid())){
            DeleteOrderResult deleteOrderResult = new DeleteOrderResult();

            DeleteOrderInfo deleteOrderInfo = new DeleteOrderInfo();
            deleteOrderInfo.setOrderId(request.getOrderId());

            if(request.getTrainNumber().startsWith("G") || request.getTrainNumber().startsWith("D") ){
                System.out.println("[Admin Order Service][Delete Order]");
                deleteOrderResult = restTemplate.postForObject(
                        "http://ts-order-service:12031/order/delete", deleteOrderInfo,DeleteOrderResult.class);
            }
            else{
                System.out.println("[Admin Order Service][Delete Order Other]");
                deleteOrderResult = restTemplate.postForObject(
                        "http://ts-order-other-service:12032/orderOther/delete", deleteOrderInfo,DeleteOrderResult.class);
            }
            return deleteOrderResult;
        }
        else{
            System.out.println("[Admin Order Service][Wrong Admin ID]");
            DeleteOrderResult result = new DeleteOrderResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + request.getLoginid());
            return result;
        }
    }

    @Override
    public UpdateOrderResult updateOrder(UpdateOrderRequest request) {
        if(checkId(request.getLoginid())){
            UpdateOrderResult updateOrderResult = new UpdateOrderResult();
            if(request.getOrder().getTrainNumber().startsWith("G") || request.getOrder().getTrainNumber().startsWith("D") ){
                System.out.println("[Admin Order Service][Update Order]");
                updateOrderResult = restTemplate.postForObject(
                        "http://ts-order-service:12031/order/adminUpdate", request.getOrder() ,UpdateOrderResult.class);
            }
            else{
                System.out.println("[Admin Order Service][Add New Order Other]");
                updateOrderResult = restTemplate.postForObject(
                        "http://ts-order-other-service:12032/orderOther/adminUpdate", request.getOrder() ,UpdateOrderResult.class);
            }
            return updateOrderResult;
        }
        else{
            System.out.println("[Admin Order Service][Wrong Admin ID]");
            UpdateOrderResult result = new UpdateOrderResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + request.getLoginid());
            return result;
        }
    }

    @Override
    public AddOrderResult addOrder(AddOrderRequest request) {
        if(checkId(request.getLoginid())){
            AddOrderResult addOrderResult;
            if(request.getOrder().getTrainNumber().startsWith("G") || request.getOrder().getTrainNumber().startsWith("D") ){
                System.out.println("[Admin Order Service][Add New Order]");
                addOrderResult = restTemplate.postForObject(
                        "http://ts-order-service:12031/order/adminAddOrder", request.getOrder() ,AddOrderResult.class);
            }
            else{
                System.out.println("[Admin Order Service][Add New Order Other]");
                addOrderResult = restTemplate.postForObject(
                        "http://ts-order-other-service:12032/orderOther/adminAddOrder", request.getOrder() ,AddOrderResult.class);
            }
            return addOrderResult;
        }
        else{
            System.out.println("[Admin Order Service][Wrong Admin ID]");
            AddOrderResult result = new AddOrderResult();
            result.setStatus(false);
            result.setMessage("The loginId is Wrong: " + request.getLoginid());
            return result;
        }
    }

    private boolean checkId(String id){
        if("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(id)){
            return true;
        }
        else{
            return false;
        }
    }
}
