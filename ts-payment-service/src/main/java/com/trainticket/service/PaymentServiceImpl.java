package com.trainticket.service;

import com.trainticket.domain.AddMoney;
import com.trainticket.domain.AddMoneyInfo;
import com.trainticket.domain.Payment;
import com.trainticket.domain.PaymentInfo;
import com.trainticket.repository.AddMoneyRepository;
import com.trainticket.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2017/6/23.
 */
@Service
public class PaymentServiceImpl implements PaymentService{

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    AddMoneyRepository addMoneyRepository;

    @Override
    public boolean pay(PaymentInfo info){
        if(paymentRepository.findByOrderId(info.getOrderId()) == null){
            Payment payment = new Payment();
            payment.setOrderId(info.getOrderId());
            payment.setPrice(info.getPrice());
            payment.setUserId(info.getUserId());
            paymentRepository.save(payment);
            return true;
        }else{
            return false;
        }
    }

    @Override
    public boolean addMoney(AddMoneyInfo info){
        AddMoney addMoney = new AddMoney();
        addMoney.setUserId(info.getUserId());
        addMoney.setMoney(info.getMoney());
        addMoneyRepository.save(addMoney);
        return true;
    }

    @Override
    public List<Payment> query(){
        return paymentRepository.findAll();
    }

    @Override
    public void initPayment(Payment payment){
        Payment paymentTemp = paymentRepository.findById(payment.getId());
        if(paymentTemp == null){
            paymentRepository.save(payment);
        }else{
            System.out.println("[Payment Service][Init Payment] Already Exists:" + payment.getId());
        }
    }
}
