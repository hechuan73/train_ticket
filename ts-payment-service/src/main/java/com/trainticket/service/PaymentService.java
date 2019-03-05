package com.trainticket.service;

import com.trainticket.entity.AddMoneyInfo;
import com.trainticket.entity.Payment;
import com.trainticket.entity.PaymentInfo;
import org.springframework.http.HttpHeaders;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/4/5.
 */
public interface PaymentService {

    boolean pay(PaymentInfo info, HttpHeaders headers);

    boolean addMoney(AddMoneyInfo info, HttpHeaders headers);

    List<Payment> query(HttpHeaders headers);

    void initPayment(Payment payment,HttpHeaders headers);

}
