package cancel.controller;

import cancel.service.CancelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/cancelservice")
public class CancelController {

    @Autowired
    CancelService cancelService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Cancel Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/cancel/refound/{orderId}")
    public HttpEntity calculate(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Cancel Order Service][Calculate Cancel Refund] OrderId:" + orderId);
        return ok(cancelService.calculateRefund(orderId, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/cancel/{orderId}/{loginId}")
    public HttpEntity cancelTicket(@PathVariable String orderId, @PathVariable String loginId,
                                   @RequestHeader HttpHeaders headers) {

        System.out.println("[Cancel Order Service][Cancel Ticket] info:" + orderId);
        try {
            System.out.println("[Cancel Order Service][Cancel Ticket] Verify Success");
            return ok(cancelService.cancelOrder(orderId, loginId, headers));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

//    private VerifyResult verifySsoLogin(String loginToken, @RequestHeader HttpHeaders headers) {
//        System.out.println("[Order Service][Verify Login] Verifying....");
//
//        HttpEntity requestTokenResult = new HttpEntity(null, headers);
//        ResponseEntity<VerifyResult> reTokenResult = restTemplate.exchange(
//                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
//                HttpMethod.GET,
//                requestTokenResult,
//                VerifyResult.class);
//        VerifyResult tokenResult = reTokenResult.getBody();
////        VerifyResult tokenResult = restTemplate.getForObject(
////                "http://ts-sso-service:12349/verifyLoginToken/" + loginToken,
////                VerifyResult.class);
//
//
//        return tokenResult;
//    }

}
