package preserveOther.controller;

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
import preserveOther.entity.OrderTicketsInfo;
import preserveOther.service.PreserveOtherService;

@RunWith(JUnit4.class)
public class PreserveOtherControllerTest {

    @InjectMocks
    private PreserveOtherController preserveOtherController;

    @Mock
    private PreserveOtherService preserveService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = preserveOtherController.home();
        Assert.assertEquals("Welcome to [ PreserveOther Service ] !", result);
    }

    @Test
    public void testPreserve(){
        OrderTicketsInfo oti = new OrderTicketsInfo();
        Mockito.when(preserveService.preserve(oti, headers)).thenReturn(response);
        httpEntity = preserveOtherController.preserve(oti, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
