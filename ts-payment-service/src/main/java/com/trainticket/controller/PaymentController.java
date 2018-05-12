package com.trainticket.controller;

import com.trainticket.domain.AddMoneyInfo;
import com.trainticket.domain.Payment;
import com.trainticket.domain.PaymentInfo;
import com.trainticket.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.*;


@RestController
public class PaymentController {

    @Autowired
    PaymentService service;

    @RequestMapping(path="/payment/pay",method= RequestMethod.POST)
    public boolean pay(@RequestBody PaymentInfo info){
        return service.pay(info);
    }

    @RequestMapping(path="/payment/addMoney",method= RequestMethod.POST)
    public boolean addMoney(@RequestBody AddMoneyInfo info){
        return service.addMoney(info);
    }

    @RequestMapping(path="/payment/query",method= RequestMethod.GET)
    public List<Payment> query(){
        return service.query();
    }
}
