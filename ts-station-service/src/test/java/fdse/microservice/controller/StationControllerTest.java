package fdse.microservice.controller;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.Station;
import fdse.microservice.service.StationService;
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

import java.util.ArrayList;
import java.util.List;

@RunWith(JUnit4.class)
public class StationControllerTest {

    @InjectMocks
    private StationController stationController;

    @Mock
    private StationService stationService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Station Service ] !", stationController.home(headers));
    }

    @Test
    public void testQuery(){
        Mockito.when(stationService.query(headers)).thenReturn(response);
        httpEntity = stationController.query(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreate(){
        Station station = new Station();
        Mockito.when(stationService.create(station, headers)).thenReturn(response);
        httpEntity = stationController.create(station, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testUpdate(){
        Station station = new Station();
        Mockito.when(stationService.update(station, headers)).thenReturn(response);
        httpEntity = stationController.update(station, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDelete(){
        Station station = new Station();
        Mockito.when(stationService.delete(station, headers)).thenReturn(response);
        httpEntity = stationController.delete(station, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryForStationId(){
        Mockito.when(stationService.queryForId("stationName", headers)).thenReturn(response);
        httpEntity = stationController.queryForStationId("stationName", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryForIdBatch(){
        List<String> stationNameList = new ArrayList<>();
        Mockito.when(stationService.queryForIdBatch(stationNameList, headers)).thenReturn(response);
        httpEntity = stationController.queryForIdBatch(stationNameList, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryById(){
        Mockito.when(stationService.queryById("stationId", headers)).thenReturn(response);
        httpEntity = stationController.queryById("stationId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryForNameBatch(){
        List<String> stationIdList = new ArrayList<>();
        Mockito.when(stationService.queryByIdBatch(stationIdList, headers)).thenReturn(response);
        httpEntity = stationController.queryForNameBatch(stationIdList, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
