package travel2.controller;

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
import travel2.entity.TravelInfo;
import travel2.entity.TripAllDetailInfo;
import travel2.entity.TripInfo;
import travel2.entity.TripResponse;
import travel2.service.Travel2Service;

import java.util.ArrayList;
import java.util.Date;

@RunWith(JUnit4.class)
public class Travel2ControllerTest {

    @InjectMocks
    private Travel2Controller travel2Controller;

    @Mock
    private Travel2Service service;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Travle2 Service ] !", travel2Controller.home(headers));
    }

    @Test
    public void testGetTrainTypeByTripId(){
        Mockito.when(service.getTrainTypeByTripId("tripId", headers)).thenReturn(response);
        httpEntity = travel2Controller.getTrainTypeByTripId("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetRouteByTripId(){
        Mockito.when(service.getRouteByTripId("tripId", headers)).thenReturn(response);
        httpEntity = travel2Controller.getRouteByTripId("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetTripsByRouteId(){
        ArrayList<String> routeIds = new ArrayList<>();
        Mockito.when(service.getTripByRoute(routeIds, headers)).thenReturn(response);
        httpEntity = travel2Controller.getTripsByRouteId(routeIds, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateTrip(){
        TravelInfo routeIds = new TravelInfo();
        Mockito.when(service.create(routeIds, headers)).thenReturn(response);
        httpEntity = travel2Controller.createTrip(routeIds, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.CREATED), httpEntity);
    }

    @Test
    public void testRetrieve(){
        Mockito.when(service.retrieve("tripId", headers)).thenReturn(response);
        httpEntity = travel2Controller.retrieve("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateTrip(){
        TravelInfo info = new TravelInfo();
        Mockito.when(service.update(info, headers)).thenReturn(response);
        httpEntity = travel2Controller.updateTrip(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteTrip(){
        Mockito.when(service.delete("tripId", headers)).thenReturn(response);
        httpEntity = travel2Controller.deleteTrip("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryInfo1(){
        TripInfo info = new TripInfo();
        ArrayList<TripResponse> errorList = new ArrayList<>();
        httpEntity = travel2Controller.queryInfo(info, headers);
        Assert.assertEquals(new ResponseEntity<>(errorList, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryInfo2(){
        TripInfo info = new TripInfo("startingPlace", "endPlace", new Date());
        Mockito.when(service.query(info, headers)).thenReturn(response);
        httpEntity = travel2Controller.queryInfo(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetTripAllDetailInfo(){
        TripAllDetailInfo gtdi = new TripAllDetailInfo();
        Mockito.when(service.getTripAllDetailInfo(gtdi, headers)).thenReturn(response);
        httpEntity = travel2Controller.getTripAllDetailInfo(gtdi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryAll(){
        Mockito.when(service.queryAll(headers)).thenReturn(response);
        httpEntity = travel2Controller.queryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAdminQueryAll(){
        Mockito.when(service.adminQueryAll(headers)).thenReturn(response);
        httpEntity = travel2Controller.adminQueryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
