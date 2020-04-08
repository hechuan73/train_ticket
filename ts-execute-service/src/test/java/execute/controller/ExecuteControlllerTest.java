package execute.controller;

import edu.fudan.common.util.Response;
import execute.serivce.ExecuteService;
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
public class ExecuteControlllerTest {

    @InjectMocks
    private ExecuteControlller executeController;

    @Mock
    private ExecuteService executeService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = executeController.home(headers);
        Assert.assertEquals("Welcome to [ Execute Service ] !", result);
    }

    @Test
    public void testExecuteTicket(){
        Mockito.when(executeService.ticketExecute("orderId", headers)).thenReturn(response);
        httpEntity = executeController.executeTicket("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCollectTicket(){
        Mockito.when(executeService.ticketCollect("orderId", headers)).thenReturn(response);
        httpEntity = executeController.collectTicket("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
