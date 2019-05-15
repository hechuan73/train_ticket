package execute.serivce;

import edu.fudan.common.util.Response;
import execute.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExecuteServiceImpl implements ExecuteService {

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response ticketExecute(String orderId, HttpHeaders headers) {
        //1.获取订单信息

        Response<Order> resultFromOrder = getOrderByIdFromOrder(orderId, headers);
        //  TicketExecuteResult result = new TicketExecuteResult();
        Order order;
        if (resultFromOrder.getStatus() == 1) {
            order =   resultFromOrder.getData();
            //2.检查订单是否可以进站
            if (order.getStatus() != OrderStatus.COLLECTED.getCode()) {
                return new Response<>(0, "Order Status Wrong", null);
            }
            //3.确认进站 请求修改订单信息

            Response resultExecute = executeOrder(orderId, OrderStatus.USED.getCode(), headers);
            if (resultExecute.getStatus() == 1) {
                return new Response<>(1, "Success.", null);
            } else {
                return new Response<>(0, resultExecute.getMsg(), null);
            }
        } else {
            resultFromOrder = getOrderByIdFromOrderOther(orderId, headers);
            if (resultFromOrder.getStatus() == 1) {
                order =   resultFromOrder.getData();
                //2.检查订单是否可以进站
                if (order.getStatus() != OrderStatus.COLLECTED.getCode()) {
                    return new Response<>(0, "Order Status Wrong", null);
                }
                //3.确认进站 请求修改订单信息

                Response resultExecute = executeOrderOther(orderId, OrderStatus.USED.getCode(), headers);
                if (resultExecute.getStatus() == 1) {
                    return new Response<>(1, "Success", null);
                } else {
                    return new Response<>(0, resultExecute.getMsg(), null);
                }
            } else {
                return new Response<>(0, "Order Not Found", null);
            }
        }
    }

    @Override
    public Response ticketCollect(String orderId, HttpHeaders headers) {
        //1.获取订单信息

        Response<Order> resultFromOrder = getOrderByIdFromOrder(orderId, headers);
        // TicketExecuteResult result = new TicketExecuteResult();
        Order order;
        if (resultFromOrder.getStatus() == 1) {
            order =  resultFromOrder.getData();
            //2.检查订单是否可以进站
            if (order.getStatus() != OrderStatus.PAID.getCode() && order.getStatus() != OrderStatus.CHANGE.getCode()) {
                return new Response<>(0, "Order Status Wrong", null);
            }
            //3.确认进站 请求修改订单信息

            Response resultExecute = executeOrder(orderId, OrderStatus.COLLECTED.getCode(), headers);
            if (resultExecute.getStatus() == 1) {
                return new Response<>(1, "Success", null);
            } else {
                return new Response<>(0, resultExecute.getMsg(), null);
            }
        } else {
            resultFromOrder = getOrderByIdFromOrderOther(orderId, headers);
            if (resultFromOrder.getStatus() == 1) {
                order = (Order) resultFromOrder.getData();
                //2.检查订单是否可以进站
                if (order.getStatus() != OrderStatus.PAID.getCode() && order.getStatus() != OrderStatus.CHANGE.getCode()) {
                    return new Response<>(0, "Order Status Wrong", null);
                }
                //3.确认进站 请求修改订单信息
                Response resultExecute = executeOrderOther(orderId, OrderStatus.COLLECTED.getCode(), headers);
                if (resultExecute.getStatus() == 1) {
                    return new Response<>(1, "Success.", null);
                } else {
                    return new Response<>(0, resultExecute.getMsg(), null);
                }
            } else {
                return new Response<>(0, "Order Not Found", null);
            }
        }
    }


    private Response executeOrder(String orderId, int status, HttpHeaders headers) {
        System.out.println("[Execute Service][Execute Order] Executing....");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/status/" + orderId + "/" + status,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response cor = re.getBody();
        return cor;
    }


    private Response executeOrderOther(String orderId, int status, HttpHeaders headers) {
        System.out.println("[Execute Service][Execute Order] Executing....");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response> re = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/status/" + orderId + "/" + status,
                HttpMethod.GET,
                requestEntity,
                Response.class);
        Response cor = re.getBody();
        return cor;
    }

    private Response<Order> getOrderByIdFromOrder(String orderId, HttpHeaders headers) {
        System.out.println("[Execute Service][Get Order] Getting....");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<Order>> re = restTemplate.exchange(
                "http://ts-order-service:12031/api/v1/orderservice/order/" + orderId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                });
        Response<Order> cor = re.getBody();
        return cor;
    }

    private Response<Order> getOrderByIdFromOrderOther(String orderId, HttpHeaders headers) {
        System.out.println("[Execute Service][Get Order] Getting....");
        HttpEntity requestEntity = new HttpEntity(headers);
        ResponseEntity<Response<Order>> re = restTemplate.exchange(
                "http://ts-order-other-service:12032/api/v1/orderOtherService/orderOther/" + orderId,
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<Order>>() {
                });
        Response<Order> cor = re.getBody();
        return cor;
    }

}
