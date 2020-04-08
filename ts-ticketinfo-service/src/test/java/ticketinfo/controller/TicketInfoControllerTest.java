package ticketinfo.controller;

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
import ticketinfo.entity.Travel;
import ticketinfo.service.TicketInfoService;

@RunWith(JUnit4.class)
public class TicketInfoControllerTest {

    @InjectMocks
    private TicketInfoController ticketInfoController;

    @Mock
    private TicketInfoService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ TicketInfo Service ] !", ticketInfoController.home());
    }

    @Test
    public void testQueryForTravel(){
        Travel info = new Travel();
        Mockito.when(service.queryForTravel(info, headers)).thenReturn(response);
        httpEntity = ticketInfoController.queryForTravel(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryForStationId(){
        Mockito.when(service.queryForStationId("name", headers)).thenReturn(response);
        httpEntity = ticketInfoController.queryForStationId("name", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
