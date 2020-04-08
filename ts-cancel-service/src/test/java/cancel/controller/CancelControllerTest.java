package cancel.controller;

import cancel.service.CancelService;
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
public class CancelControllerTest {

    @InjectMocks
    private CancelController cancelController;

    @Mock
    private CancelService cancelService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = cancelController.home(headers);
        Assert.assertEquals("Welcome to [ Cancel Service ] !", result);
    }

    @Test
    public void testCalculate(){
        Mockito.when(cancelService.calculateRefund("orderId", headers)).thenReturn(response);
        httpEntity = cancelController.calculate("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCancelTicket(){
        Mockito.when(cancelService.cancelOrder("orderId", "loginId", headers)).thenReturn(response);
        httpEntity = cancelController.cancelTicket("orderId", "loginId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
