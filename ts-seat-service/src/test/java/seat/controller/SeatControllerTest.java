package seat.controller;

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
import seat.entity.Seat;
import seat.service.SeatService;

@RunWith(JUnit4.class)
public class SeatControllerTest {

    @InjectMocks
    private SeatController seatController;

    @Mock
    private SeatService seatService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Seat Service ] !", seatController.home());
    }

    @Test
    public void testCreate(){
        Seat seatRequest = new Seat();
        Mockito.when(seatService.distributeSeat(seatRequest, headers)).thenReturn(response);
        httpEntity = seatController.create(seatRequest, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetLeftTicketOfInterval(){
        Seat seatRequest = new Seat();
        Mockito.when(seatService.getLeftTicketOfInterval(seatRequest, headers)).thenReturn(response);
        httpEntity = seatController.getLeftTicketOfInterval(seatRequest, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
