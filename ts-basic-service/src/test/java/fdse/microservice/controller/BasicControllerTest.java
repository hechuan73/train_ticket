package fdse.microservice.controller;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.Travel;
import fdse.microservice.service.BasicService;
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
public class BasicControllerTest {

    @InjectMocks
    private BasicController basicController;

    @Mock
    private BasicService basicService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = basicController.home(headers);
        Assert.assertEquals("Welcome to [ Basic Service ] !", result);
    }

    @Test
    public void testQueryForTravel(){
        Travel info = new Travel();
        Mockito.when(basicService.queryForTravel(info, headers)).thenReturn(response);
        httpEntity = basicController.queryForTravel(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryForStationId(){
        Mockito.when(basicService.queryForStationId("stationName", headers)).thenReturn(response);
        httpEntity = basicController.queryForStationId("stationName", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
