package consign.controller;

import consign.entity.Consign;
import consign.service.ConsignService;
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

import java.util.UUID;

@RunWith(JUnit4.class)
public class ConsignControllerTest {

    @InjectMocks
    private ConsignController consignController;

    @Mock
    private ConsignService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = consignController.home(headers);
        Assert.assertEquals("Welcome to [ Consign Service ] !", result);
    }

    @Test
    public void testInsertConsign(){
        Consign request = new Consign();
        Mockito.when(service.insertConsignRecord(request, headers)).thenReturn(response);
        httpEntity = consignController.insertConsign(request, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateConsign(){
        Consign request = new Consign();
        Mockito.when(service.updateConsignRecord(request, headers)).thenReturn(response);
        httpEntity = consignController.updateConsign(request, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindByAccountId(){
        UUID id = UUID.randomUUID();
        Mockito.when(service.queryByAccountId(id, headers)).thenReturn(response);
        httpEntity = consignController.findByAccountId(id.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindByOrderId(){
        UUID id = UUID.randomUUID();
        Mockito.when(service.queryByOrderId(id, headers)).thenReturn(response);
        httpEntity = consignController.findByOrderId(id.toString(), headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindByConsignee(){
        Mockito.when(service.queryByConsignee("consignee", headers)).thenReturn(response);
        httpEntity = consignController.findByConsignee("consignee", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
