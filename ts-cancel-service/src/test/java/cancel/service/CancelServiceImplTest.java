package cancel.service;

import cancel.entity.NotifyInfo;
import cancel.entity.Order;
import cancel.entity.User;
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
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RunWith(JUnit4.class)
public class CancelServiceImplTest {

    @InjectMocks
    private CancelServiceImpl cancelServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCancelOrder() {

    }

    @Test
    public void testSendEmail() {
        NotifyInfo notifyInfo = new NotifyInfo();
        HttpEntity requestEntity2 = new HttpEntity(notifyInfo, headers);
        ResponseEntity<Boolean> re = new ResponseEntity<>(true, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-notification-service:17853/api/v1/notifyservice/notification/order_cancel_success",
                HttpMethod.POST,
                requestEntity2,
                Boolean.class)).thenReturn(re);
        Boolean result = cancelServiceImpl.sendEmail(notifyInfo, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testCalculateRefund() {

    }

    @Test
    public void testDrawbackMoney() {
        Response response = new Response<>(1, null, null);
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-inside-payment-service:18673/api/v1/inside_pay_service/inside_payment/drawback/" + "userId" + "/" + "money",
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re);
        Boolean result = cancelServiceImpl.drawbackMoney("money", "userId", headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testGetAccount() {
        Response<User> response = new Response<>();
        ResponseEntity<Response<User>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-user-service:12342/api/v1/userservice/users/id/" + "orderId",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<User>>() {
                })).thenReturn(re);
        Response<User> result = cancelServiceImpl.getAccount("orderId", headers);
        Assert.assertEquals(new Response<User>(null, null, null), result);
    }

}
