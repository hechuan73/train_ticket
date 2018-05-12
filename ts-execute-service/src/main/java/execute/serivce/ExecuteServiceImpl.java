package execute.serivce;

import execute.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExecuteServiceImpl implements ExecuteService{

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public TicketExecuteResult ticketExecute(TicketExecuteInfo info){

        GetOrderByIdInfo getOrderByIdInfo = new GetOrderByIdInfo();
        getOrderByIdInfo.setOrderId(info.getOrderId());
        GetOrderResult resultFromOrder = getOrderByIdFromOrder(getOrderByIdInfo);
        TicketExecuteResult result = new TicketExecuteResult();
        Order order;
        if(resultFromOrder.isStatus() == true){
            order = resultFromOrder.getOrder();

            if(order.getStatus() != OrderStatus.COLLECTED.getCode()){
                result.setStatus(false);
                result.setMessage("Order Status Wrong");
                return result;
            }

            ModifyOrderStatusInfo executeInfo = new ModifyOrderStatusInfo();
            executeInfo.setOrderId(info.getOrderId());
            executeInfo.setStatus(OrderStatus.USED.getCode());
            ModifyOrderStatusResult resultExecute = executeOrder(executeInfo);
            if(resultExecute.isStatus() == true){
                result.setStatus(true);
                result.setMessage("Success.");
                return result;
            }else{
                result.setStatus(false);
                result.setMessage(resultExecute.getMessage());
                return result;
            }
        }else{
            resultFromOrder = getOrderByIdFromOrderOther(getOrderByIdInfo);
            if(resultFromOrder.isStatus() == true){
                order = resultFromOrder.getOrder();

                if(order.getStatus() != OrderStatus.COLLECTED.getCode()){
                    result.setStatus(false);
                    result.setMessage("Order Status Wrong");
                    return result;
                }

                ModifyOrderStatusInfo executeInfo = new  ModifyOrderStatusInfo();
                executeInfo.setOrderId(info.getOrderId());
                executeInfo.setStatus(OrderStatus.USED.getCode());
                ModifyOrderStatusResult resultExecute = executeOrderOther(executeInfo);
                if(resultExecute.isStatus() == true){
                    result.setStatus(true);
                    result.setMessage("Success.");
                    return result;
                }else{
                    result.setStatus(false);
                    result.setMessage(resultExecute.getMessage());
                    return result;
                }
            }else{
                result.setStatus(false);
                result.setMessage("Order Not Found");
                return result;
            }
        }
    }

    @Override
    public TicketExecuteResult ticketCollect(TicketExecuteInfo info){

        GetOrderByIdInfo getOrderByIdInfo = new GetOrderByIdInfo();
        getOrderByIdInfo.setOrderId(info.getOrderId());
        GetOrderResult resultFromOrder = getOrderByIdFromOrder(getOrderByIdInfo);
        TicketExecuteResult result = new TicketExecuteResult();
        Order order;
        if(resultFromOrder.isStatus() == true){
            order = resultFromOrder.getOrder();

            if(order.getStatus() != OrderStatus.PAID.getCode()){
                result.setStatus(false);
                result.setMessage("Order Status Wrong");
                return result;
            }

            ModifyOrderStatusInfo executeInfo = new ModifyOrderStatusInfo();
            executeInfo.setOrderId(info.getOrderId());
            executeInfo.setStatus(OrderStatus.COLLECTED.getCode());
            ModifyOrderStatusResult resultExecute = executeOrder(executeInfo);
            if(resultExecute.isStatus() == true){
                result.setStatus(true);
                result.setMessage("Success.");
                return result;
            }else{
                result.setStatus(false);
                result.setMessage(resultExecute.getMessage());
                return result;
            }
        }else{
            resultFromOrder = getOrderByIdFromOrderOther(getOrderByIdInfo);
            if(resultFromOrder.isStatus() == true){
                order = resultFromOrder.getOrder();

                if(order.getStatus() != OrderStatus.PAID.getCode()){
                    result.setStatus(false);
                    result.setMessage("Order Status Wrong");
                    return result;
                }

                ModifyOrderStatusInfo executeInfo = new ModifyOrderStatusInfo();
                executeInfo.setOrderId(info.getOrderId());
                executeInfo.setStatus(OrderStatus.COLLECTED.getCode());
                ModifyOrderStatusResult resultExecute = executeOrderOther(executeInfo);
                if(resultExecute.isStatus() == true){
                    result.setStatus(true);
                    result.setMessage("Success.");
                    return result;
                }else{
                    result.setStatus(false);
                    result.setMessage(resultExecute.getMessage());
                    return result;
                }
            }else{
                result.setStatus(false);
                result.setMessage("Order Not Found");
                return result;
            }
        }
    }


    private ModifyOrderStatusResult executeOrder(ModifyOrderStatusInfo info){
        System.out.println("[Execute Service][Execute Order] Executing....");
        ModifyOrderStatusResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/modifyOrderStatus"
                ,info,ModifyOrderStatusResult.class);
        return cor;
    }

    private ModifyOrderStatusResult executeOrderOther(ModifyOrderStatusInfo info){
        System.out.println("[Execute Service][Execute Order] Executing....");
        ModifyOrderStatusResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/order/modifyOrderStatus"
                ,info,ModifyOrderStatusResult.class);
        return cor;
    }

    private GetOrderResult getOrderByIdFromOrder(GetOrderByIdInfo info){
        System.out.println("[Execute Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-service:12031/order/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

    private GetOrderResult getOrderByIdFromOrderOther(GetOrderByIdInfo info){
        System.out.println("[Execute Service][Get Order] Getting....");
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,info,GetOrderResult.class);
        return cor;
    }

}
