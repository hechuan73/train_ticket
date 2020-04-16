package preserveOther.service;

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
import preserveOther.entity.NotifyInfo;
import preserveOther.entity.Seat;
import preserveOther.entity.Ticket;
import preserveOther.entity.User;

import java.util.Date;

@RunWith(JUnit4.class)
public class PreserveOtherServiceImplTest {

    @InjectMocks
    private PreserveOtherServiceImpl preserveOtherServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testPreserve() {

    }

    @Test
    public void testDipatchSeat() {
        long mills = System.currentTimeMillis();
        Seat seatRequest = new Seat(new Date(mills), "G1234", "start_station", "dest_station", 2);
        HttpEntity requestEntityTicket = new HttpEntity(seatRequest, headers);
        Response<Ticket> response = new Response<>();
        ResponseEntity<Response<Ticket>> reTicket = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-seat-service:18898/api/v1/seatservice/seats",
                HttpMethod.POST,
                requestEntityTicket,
                new ParameterizedTypeReference<Response<Ticket>>() {
                })).thenReturn(reTicket);
        Ticket result = preserveOtherServiceImpl.dipatchSeat(new Date(mills), "G1234", "start_station", "dest_station", 2, headers);
        Assert.assertNull(result);
    }

    @Test
    public void testSendEmail() {
        NotifyInfo notifyInfo = new NotifyInfo();
        HttpEntity requestEntitySendEmail = new HttpEntity(notifyInfo, headers);
        ResponseEntity<Boolean> reSendEmail = new ResponseEntity<>(true, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-notification-service:17853/api/v1/notifyservice/notification/order_cancel_success",
                HttpMethod.POST,
                requestEntitySendEmail,
                Boolean.class)).thenReturn(reSendEmail);
        boolean result = preserveOtherServiceImpl.sendEmail(notifyInfo, headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testGetAccount() {
        Response<User> response = new Response<>();
        ResponseEntity<Response<User>> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-user-service:12342/api/v1/userservice/users/id/1",
                HttpMethod.GET,
                requestEntity,
                new ParameterizedTypeReference<Response<User>>() {
                })).thenReturn(re);
        User result = preserveOtherServiceImpl.getAccount("1", headers);
        Assert.assertNull(result);
    }

}
