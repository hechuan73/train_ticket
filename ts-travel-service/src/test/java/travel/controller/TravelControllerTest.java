package travel.controller;

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
import travel.entity.TravelInfo;
import travel.entity.TripAllDetailInfo;
import travel.entity.TripInfo;
import travel.entity.TripResponse;
import travel.service.TravelService;

import java.util.ArrayList;
import java.util.Date;

@RunWith(JUnit4.class)
public class TravelControllerTest {

    @InjectMocks
    private TravelController travelController;

    @Mock
    private TravelService service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Travel Service ] !", travelController.home(headers));
    }

    @Test
    public void testGetTrainTypeByTripId(){
        Mockito.when(service.getTrainTypeByTripId("tripId", headers)).thenReturn(response);
        httpEntity = travelController.getTrainTypeByTripId("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetRouteByTripId(){
        Mockito.when(service.getRouteByTripId("tripId", headers)).thenReturn(response);
        httpEntity = travelController.getRouteByTripId("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetTripsByRouteId(){
        ArrayList<String> routeIds = new ArrayList<>();
        Mockito.when(service.getTripByRoute(routeIds, headers)).thenReturn(response);
        httpEntity = travelController.getTripsByRouteId(routeIds, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateTrip(){
        TravelInfo routeIds = new TravelInfo();
        Mockito.when(service.create(routeIds, headers)).thenReturn(response);
        httpEntity = travelController.createTrip(routeIds, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testRetrieve(){
        Mockito.when(service.retrieve("tripId", headers)).thenReturn(response);
        httpEntity = travelController.retrieve("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateTrip(){
        TravelInfo info = new TravelInfo();
        Mockito.when(service.update(info, headers)).thenReturn(response);
        httpEntity = travelController.updateTrip(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteTrip(){
        Mockito.when(service.delete("tripId", headers)).thenReturn(response);
        httpEntity = travelController.deleteTrip("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryInfo1(){
        TripInfo info = new TripInfo();
        ArrayList<TripResponse> errorList = new ArrayList<>();
        httpEntity = travelController.queryInfo(info, headers);
        Assert.assertEquals(new ResponseEntity<>(errorList, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryInfo2(){
        TripInfo info = new TripInfo("startingPlace", "endPlace", new Date());
        Mockito.when(service.query(info, headers)).thenReturn(response);
        httpEntity = travelController.queryInfo(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetTripAllDetailInfo(){
        TripAllDetailInfo gtdi = new TripAllDetailInfo();
        Mockito.when(service.getTripAllDetailInfo(gtdi, headers)).thenReturn(response);
        httpEntity = travelController.getTripAllDetailInfo(gtdi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryAll(){
        Mockito.when(service.queryAll(headers)).thenReturn(response);
        httpEntity = travelController.queryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAdminQueryAll(){
        Mockito.when(service.adminQueryAll(headers)).thenReturn(response);
        httpEntity = travelController.adminQueryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
