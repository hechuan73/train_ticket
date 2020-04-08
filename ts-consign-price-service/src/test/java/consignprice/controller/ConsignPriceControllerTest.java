package consignprice.controller;

import consignprice.entity.ConsignPrice;
import consignprice.service.ConsignPriceService;
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
public class ConsignPriceControllerTest {

    @InjectMocks
    private ConsignPriceController consignPriceController;

    @Mock
    private ConsignPriceService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ ConsignPrice Service ] !", consignPriceController.home(headers));
    }

    @Test
    public void testGetPriceByWeightAndRegion(){
        Mockito.when(service.getPriceByWeightAndRegion(Double.parseDouble("1.0"),
                Boolean.parseBoolean("true"), headers)).thenReturn(response);
        httpEntity = consignPriceController.getPriceByWeightAndRegion("1.0", "true", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetPriceInfo(){
        Mockito.when(service.queryPriceInformation(headers)).thenReturn(response);
        httpEntity = consignPriceController.getPriceInfo(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetPriceConfig(){
        Mockito.when(service.getPriceConfig(headers)).thenReturn(response);
        httpEntity = consignPriceController.getPriceConfig(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyPriceConfig(){
        ConsignPrice priceConfig = new ConsignPrice();
        Mockito.when(service.createAndModifyPrice(priceConfig, headers)).thenReturn(response);
        httpEntity = consignPriceController.modifyPriceConfig(priceConfig, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
