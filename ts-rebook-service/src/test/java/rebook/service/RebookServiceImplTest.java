package rebook.service;

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
import rebook.entity.Seat;
import rebook.entity.Ticket;

import java.util.Date;

@RunWith(JUnit4.class)
public class RebookServiceImplTest {

    @InjectMocks
    private RebookServiceImpl rebookServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRebook() {

    }

    @Test
    public void testPayDifference() {

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
        Ticket result = rebookServiceImpl.dipatchSeat(new Date(mills), "G1234", "start_station", "dest_station", 2, headers);
        Assert.assertNull(result);
    }

}
