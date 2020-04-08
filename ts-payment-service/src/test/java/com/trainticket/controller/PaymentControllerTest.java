package com.trainticket.controller;

import com.trainticket.entity.Payment;
import com.trainticket.service.PaymentService;
import edu.fudan.common.util.Response;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@RunWith(JUnit4.class)
public class PaymentControllerTest {

    @InjectMocks
    private PaymentController paymentController;

    @Mock
    private PaymentService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = paymentController.home();
        Assert.assertEquals("Welcome to [ Payment Service ] !", result);
    }

    @Test
    public void testPay(){
        Payment info = new Payment();
        Mockito.when(service.pay(info, headers)).thenReturn(response);
        httpEntity = paymentController.pay(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddMoney(){
        Payment info = new Payment();
        Mockito.when(service.addMoney(info, headers)).thenReturn(response);
        httpEntity = paymentController.addMoney(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQuery(){
        Mockito.when(service.query(headers)).thenReturn(response);
        httpEntity = paymentController.query(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
