package com.trainticket.service;

import com.trainticket.entity.Payment;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

/**
 * Created by Chenjie Xu on 2017/4/5.
 */
public interface PaymentService {

    Response pay(Payment info, HttpHeaders headers);

    Response addMoney(Payment info, HttpHeaders headers);

    Response query(HttpHeaders headers);

    void initPayment(Payment payment,HttpHeaders headers);

}
