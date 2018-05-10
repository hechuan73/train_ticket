package com.trainticket.service;

import com.trainticket.domain.AddMoneyInfo;
import com.trainticket.domain.Payment;
import com.trainticket.domain.PaymentInfo;

import java.util.List;


public interface PaymentService {

    boolean pay(PaymentInfo info);

    boolean addMoney(AddMoneyInfo info);

    List<Payment> query();

    void initPayment(Payment payment);

}
