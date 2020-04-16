package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.PriceConfig;
import fdse.microservice.entity.Route;
import fdse.microservice.entity.TrainType;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@RunWith(JUnit4.class)
public class BasicServiceImplTest {

    @InjectMocks
    private BasicServiceImpl basicServiceImpl;

    @Mock
    private RestTemplate restTemplate;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity requestEntity = new HttpEntity(headers);

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testQueryForTravel() {

    }

    @Test
    public void testQueryForStationId() {
        Response response = new Response<>();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + "stationName",
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re);
        Response result = basicServiceImpl.queryForStationId("stationName", headers);
        Assert.assertEquals(new Response<>(null, null, null), result);
    }

    @Test
    public void testCheckStationExists() {
        Response response = new Response<>(1, null, null);
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-station-service:12345/api/v1/stationservice/stations/id/" + "stationName",
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re);
        Boolean result = basicServiceImpl.checkStationExists("stationName", headers);
        Assert.assertTrue(result);
    }

    @Test
    public void testQueryTrainType() {
        Response response = new Response<>();
        ResponseEntity<Response> re = new ResponseEntity<>(response, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(
                "http://ts-train-service:14567/api/v1/trainservice/trains/" + "trainTypeId",
                HttpMethod.GET,
                requestEntity,
                Response.class)).thenReturn(re);
        TrainType result = basicServiceImpl.queryTrainType("trainTypeId", headers);
        Assert.assertNull(result);
    }

}
