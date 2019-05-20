package inside_payment.controller;

import inside_payment.entity.*;
import inside_payment.service.InsidePaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/inside_pay_service")
public class InsidePaymentController {

    @Autowired
    public InsidePaymentService service;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ InsidePayment Service ] !";
    }

    @PostMapping(value = "/inside_payment")
    public HttpEntity pay(@RequestBody PaymentInfo info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Inside Payment Service][Pay] Pay for:" + info.getOrderId());
        return ok(service.pay(info, headers));
    }

    @PostMapping(value = "/inside_payment/account")
    public HttpEntity createAccount(@RequestBody AccountInfo info, @RequestHeader HttpHeaders headers) {
        return ok(service.createAccount(info, headers));
    }

    @GetMapping(value = "/inside_payment/{userId}/{money}")
    public HttpEntity addMoney(@PathVariable String userId, @PathVariable
            String money, @RequestHeader HttpHeaders headers) {
        return ok(service.addMoney(userId, money, headers));
    }

    @GetMapping(value = "/inside_payment/payment")
    public HttpEntity queryPayment(@RequestHeader HttpHeaders headers) {
        return ok(service.queryPayment(headers));
    }

    @GetMapping(value = "/inside_payment/account")
    public HttpEntity queryAccount(@RequestHeader HttpHeaders headers) {
        return ok(service.queryAccount(headers));
    }

    @GetMapping(value = "/inside_payment/drawback/{userId}/{money}")
    public HttpEntity drawBack(@PathVariable String userId, @PathVariable String money, @RequestHeader HttpHeaders headers) {
        return ok(service.drawBack(userId, money, headers));
    }

    @PostMapping(value = "/inside_payment/difference")
    public HttpEntity payDifference(@RequestBody PaymentInfo info, @RequestHeader HttpHeaders headers) {
        return ok(service.payDifference(info, headers));
    }

    @GetMapping(value = "/inside_payment/money")
    public HttpEntity queryAddMoney(@RequestHeader HttpHeaders headers) {
        return ok(service.queryAddMoney(headers));
    }

//    @RequestMapping("/hello1_callback")
//    public String hello1_callback(@RequestParam(value = "result", defaultValue = "satan") String cal_back, @RequestHeader HttpHeaders headers) {
//
//        System.out.println("Call Back Result:" + cal_back);
//        System.out.println("-------------external call back-------------");
//        return "-------call back end-------";
//
//    }
}
