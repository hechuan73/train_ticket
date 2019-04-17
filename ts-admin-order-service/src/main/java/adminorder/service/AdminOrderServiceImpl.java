package adminorder.service;

import adminorder.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AdminOrderServiceImpl implements AdminOrderService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response getAllOrders(String id, HttpHeaders headers) {
        if (checkId(id)) {
            System.out.println("[Admin Order Service][Get All Orders]");
            //Get all of the orders
            ArrayList<Order> orders = new ArrayList<Order>();
            //From ts-order-service
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<Response<ArrayList<Order>>> re = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order",
                    HttpMethod.GET,
                    requestEntity,
                    new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                    });
            Response<ArrayList<Order>> result = re.getBody();
//            QueryOrderResult result = restTemplate.getForObject(
//                    "http://ts-order-service:12031/order/findAll",
//                    QueryOrderResult.class);
            if (result.getStatus() == 1) {
                System.out.println("[Admin Order Service][Get Orders From ts-order-service successfully!]");
                ArrayList<Order> orders1 = result.getData();
                orders.addAll(orders1);
            } else
                System.out.println("[Admin Order Service][Get Orders From ts-order-service fail!]");
            //From ts-order-other-service
            HttpEntity requestEntity2 = new HttpEntity(headers);
            ResponseEntity<Response<ArrayList<Order>>> re2 = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther",
                    HttpMethod.GET,
                    requestEntity2,
                    new ParameterizedTypeReference<Response<ArrayList<Order>>>() {
                    });
            result = re2.getBody();
//            result = restTemplate.getForObject(
//                    "http://ts-order-other-service:12032/orderOther/findAll",
//                    QueryOrderResult.class);
            if (result.getStatus() == 1) {
                System.out.println("[Admin Order Service][Get Orders From ts-order-other-service successfully!]");
                ArrayList<Order> orders1 = (ArrayList<Order>) result.getData();
                orders.addAll(orders1);
            } else
                System.out.println("[Admin Order Service][Get Orders From ts-order-other-service fail!]");
            //Return orders
            return new Response<>(1, "Get the orders successfully!", orders);
        } else {
            System.out.println("[Admin Order Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + id, null);
        }
    }

    @Override
    public Response deleteOrder(String loginid, String orderId, String trainNumber, HttpHeaders headers) {
        if (checkId(loginid)) {

            Response deleteOrderResult;


            if (trainNumber.startsWith("G") || trainNumber.startsWith("D")) {
                System.out.println("[Admin Order Service][Delete Order]");
                HttpEntity requestEntity = new HttpEntity(headers);
                ResponseEntity<Response> re = restTemplate.exchange(
                        "http://ts-order-service:12031/api/v1/orderservice/order/" + orderId,
                        HttpMethod.DELETE,
                        requestEntity,
                        Response.class);
                deleteOrderResult = re.getBody();
//                deleteOrderResult = restTemplate.postForObject(
//                        "http://ts-order-service:12031/order/delete", deleteOrderInfo,DeleteOrderResult.class);
            } else {
                System.out.println("[Admin Order Service][Delete Order Other]");
                HttpEntity requestEntity = new HttpEntity(headers);
                ResponseEntity<Response> re = restTemplate.exchange(
                        "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + orderId,
                        HttpMethod.DELETE,
                        requestEntity,
                        Response.class);
                deleteOrderResult = re.getBody();
//                deleteOrderResult = restTemplate.postForObject(
//                        "http://ts-order-other-service:12032/orderOther/delete", deleteOrderInfo,DeleteOrderResult.class);
            }
            return deleteOrderResult;
        } else {
            System.out.println("[Admin Order Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + loginid, null);
        }
    }

    @Override
    public Response updateOrder(Order request, HttpHeaders headers) {

        Response updateOrderResult;
        if (request.getTrainNumber().startsWith("G") || request.getTrainNumber().startsWith("D")) {
            System.out.println("[Admin Order Service][Update Order]");
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/adminUpdate",
                    HttpMethod.PUT,
                    requestEntity,
                    Response.class);
            updateOrderResult = re.getBody();
//                updateOrderResult = restTemplate.postForObject(
//                        "http://ts-order-service:12031/order/adminUpdate", request.getOrder() ,UpdateOrderResult.class);
        } else {
            System.out.println("[Admin Order Service][Add New Order Other]");
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/adminUpdate",
                    HttpMethod.PUT,
                    requestEntity,
                    Response.class);
            updateOrderResult = re.getBody();
//                updateOrderResult = restTemplate.postForObject(
//                        "http://ts-order-other-service:12032/orderOther/adminUpdate", request.getOrder() ,UpdateOrderResult.class);
        }
        return updateOrderResult;
    }

    @Override
    public Response addOrder(Order request, HttpHeaders headers) {

        Response addOrderResult;
        if (request.getTrainNumber().startsWith("G") || request.getTrainNumber().startsWith("D")) {
            System.out.println("[Admin Order Service][Add New Order]");
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-service:12031/api/v1/orderservice/order/admin",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            addOrderResult = re.getBody();
//                addOrderResult = restTemplate.postForObject(
//                        "http://ts-order-service:12031/order/adminAddOrder", request.getOrder() ,AddOrderResult.class);
        } else {
            System.out.println("[Admin Order Service][Add New Order Other]");
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<Response> re = restTemplate.exchange(
                    "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/admin",
                    HttpMethod.POST,
                    requestEntity,
                    Response.class);
            addOrderResult = re.getBody();
//                addOrderResult = restTemplate.postForObject(
//                        "http://ts-order-other-service:12032/orderOther/adminAddOrder", request.getOrder() ,AddOrderResult.class);
        }
        return addOrderResult;

    }

    private boolean checkId(String id) {
        if ("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(id)) {
            return true;
        } else {
            return false;
        }
    }
}
