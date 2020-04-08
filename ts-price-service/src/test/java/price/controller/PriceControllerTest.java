package price.controller;

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
import price.entity.PriceConfig;
import price.service.PriceService;

@RunWith(JUnit4.class)
public class PriceControllerTest {

    @InjectMocks
    private PriceController priceController;

    @Mock
    private PriceService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = priceController.home();
        Assert.assertEquals("Welcome to [ Price Service ] !", result);
    }

    @Test
    public void testQuery(){
        Mockito.when(service.findByRouteIdAndTrainType("routeId", "trainType", headers)).thenReturn(response);
        httpEntity = priceController.query("routeId", "trainType", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryAll(){
        Mockito.when(service.findAllPriceConfig(headers)).thenReturn(response);
        httpEntity = priceController.queryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreate(){
        PriceConfig info = new PriceConfig();
        Mockito.when(service.createNewPriceConfig(info, headers)).thenReturn(response);
        httpEntity = priceController.create(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testDelete(){
        PriceConfig info = new PriceConfig();
        Mockito.when(service.deletePriceConfig(info, headers)).thenReturn(response);
        httpEntity = priceController.delete(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdate(){
        PriceConfig info = new PriceConfig();
        Mockito.when(service.updatePriceConfig(info, headers)).thenReturn(response);
        httpEntity = priceController.update(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
