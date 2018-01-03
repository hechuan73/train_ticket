package cancel.controller;

import cancel.domain.CalculateRefundResult;
import cancel.domain.CancelOrderInfo;
import cancel.domain.CancelOrderResult;
import cancel.domain.VerifyResult;
import cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
public class CancelController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    CancelService cancelService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/cancelCalculateRefund", method = RequestMethod.POST)
    public CalculateRefundResult calculate(@RequestBody CancelOrderInfo info){
        System.out.println("[Cancel Order Service][Calculate Cancel Refund] OrderId:" + info.getOrderId());
        return cancelService.calculateRefund(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/cancelOrder", method = RequestMethod.POST)
    public CancelOrderResult cancelTicket(@RequestBody CancelOrderInfo info, @CookieValue String loginToken, @CookieValue String loginId){
        System.out.println("[Cancel Order Service][Cancel Ticket] info:" + info.getOrderId());
        if(loginToken == null ){
            loginToken = "admin";
        }
        System.out.println("[Cancel Order Service][Cancel Order] order ID:" + info.getOrderId() + "  loginToken:" + loginToken);
        if(loginToken == null){
            System.out.println("[Cancel Order Service][Cancel Order] Not receive any login token");
            CancelOrderResult result = new CancelOrderResult();
            result.setStatus(false);
            result.setMessage("No Login Token");
            return result;
        }
        VerifyResult verifyResult = verifySsoLogin(loginToken);
        if(verifyResult.isStatus() == false){
            System.out.println("[Cancel Order Service][Cancel Order] Do not login.");
            CancelOrderResult result = new CancelOrderResult();
            result.setStatus(false);
            result.setMessage("Not Login");
            return result;
        }else{
            System.out.println("[Cancel Order Service][Cancel Ticket] Verify Success");
            try{
                return cancelService.cancelOrder(info,loginToken,loginId);
            }catch(Exception e){
                e.printStackTrace();
                return null;
            }

        }
    }

    private VerifyResult verifySsoLogin(String loginToken){
        System.out.println("[Cancel Order Service][Verify Login] Verifying....");
        VerifyResult tokenResult = restTemplate.getForObject(
                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
                VerifyResult.class);
        return tokenResult;
    }

}
