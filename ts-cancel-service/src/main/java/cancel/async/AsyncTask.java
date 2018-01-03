package cancel.async;

import java.util.concurrent.Future;
import cancel.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


/** 
 * Asynchronous Tasks 
 * @author Xu 
 * 
 */  
@Component  
public class AsyncTask {  
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());  
    
    @Autowired
	private RestTemplate restTemplate;

    @Async("myAsync")
    public Future<ChangeOrderResult> updateOtherOrderStatusToCancel(ChangeOrderInfo info) throws InterruptedException{

        Thread.sleep(2000);

        System.out.println("[Cancel Order Service][Change Order Status] Getting....");
        ChangeOrderResult result = restTemplate.postForObject("http://ts-order-other-service:12032/orderOther/update",info,ChangeOrderResult.class);
        return new AsyncResult<>(result);
    }

    @Async("mySimpleAsync")
    public Future<Boolean> drawBackMoneyForOrderCan(String money, String userId,String orderId) throws InterruptedException{

        System.out.println("[Cancel Order Service][Get Order] Getting....");
        GetOrderByIdInfo getOrderInfo = new GetOrderByIdInfo();
        getOrderInfo.setOrderId(orderId);
        GetOrderResult cor = restTemplate.postForObject(
                "http://ts-order-other-service:12032/orderOther/getById/"
                ,getOrderInfo,GetOrderResult.class);
        Order order = cor.getOrder();
        if(order.getStatus() == OrderStatus.NOTPAID.getCode()
                || order.getStatus() == OrderStatus.PAID.getCode() || order.getStatus() == OrderStatus.CHANGE.getCode()){

            System.out.println("[Cancel Order Service][Draw Back Money] Draw back money...");
            DrawBackInfo info = new DrawBackInfo();
            info.setMoney(money);
            info.setUserId(userId);
            String result = restTemplate.postForObject("http://ts-inside-payment-service:18673/inside_payment/drawBack",info,String.class);
            if(result.equals("true")){
                return new AsyncResult<>(true);
            }else{
                return new AsyncResult<>(false);
            }
        }else{

            System.out.println("[Cancel Order Service][Drawback Money] Fail. Status Not Permitted");
            return new AsyncResult<>(false);

        }
    }
      
}  
