package inside_payment.controller;

import edu.fudan.common.util.Response;
import inside_payment.entity.AccountInfo;
import inside_payment.entity.PaymentInfo;
import inside_payment.service.InsidePaymentService;
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
public class InsidePaymentControllerTest {

    @InjectMocks
    private InsidePaymentController insidePaymentController;

    @Mock
    private InsidePaymentService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = insidePaymentController.home();
        Assert.assertEquals("Welcome to [ InsidePayment Service ] !", result);
    }

    @Test
    public void testPay(){
        PaymentInfo info = new PaymentInfo();
        Mockito.when(service.pay(info, headers)).thenReturn(response);
        httpEntity = insidePaymentController.pay(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateAccount(){
        AccountInfo info = new AccountInfo();
        Mockito.when(service.createAccount(info, headers)).thenReturn(response);
        httpEntity = insidePaymentController.createAccount(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddMoney(){
        Mockito.when(service.addMoney("userId", "money", headers)).thenReturn(response);
        httpEntity = insidePaymentController.addMoney("userId", "money", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryPayment(){
        Mockito.when(service.queryPayment(headers)).thenReturn(response);
        httpEntity = insidePaymentController.queryPayment(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryAccount(){
        Mockito.when(service.queryAccount(headers)).thenReturn(response);
        httpEntity = insidePaymentController.queryAccount(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDrawBack(){
        Mockito.when(service.drawBack("userId", "money", headers)).thenReturn(response);
        httpEntity = insidePaymentController.drawBack("userId", "money", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testPayDifference(){
        PaymentInfo info = new PaymentInfo();
        Mockito.when(service.payDifference(info, headers)).thenReturn(response);
        httpEntity = insidePaymentController.payDifference(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryAddMoney(){
        Mockito.when(service.queryAddMoney(headers)).thenReturn(response);
        httpEntity = insidePaymentController.queryAddMoney(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
