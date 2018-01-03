package com.trainticket.service;

import com.trainticket.domain.AddMoneyInfo;
import com.trainticket.domain.Payment;
import com.trainticket.domain.PaymentInfo;

import java.util.List;

/**
 * Created by Chenjie Xu on 2017/4/5.
 */
public interface PaymentService {

    boolean pay(PaymentInfo info);

    boolean addMoney(AddMoneyInfo info);

    List<Payment> query();

    void initPayment(Payment payment);

}
