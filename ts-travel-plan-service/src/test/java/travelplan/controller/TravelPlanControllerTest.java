package travelplan.controller;

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
import travelplan.entity.TransferTravelInfo;
import travelplan.entity.TripInfo;
import travelplan.service.TravelPlanService;

@RunWith(JUnit4.class)
public class TravelPlanControllerTest {

    @InjectMocks
    private TravelPlanController travelPlanController;

    @Mock
    private TravelPlanService travelPlanService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ TravelPlan Service ] !", travelPlanController.home());
    }

    @Test
    public void testGetTransferResult(){
        TransferTravelInfo info = new TransferTravelInfo();
        Mockito.when(travelPlanService.getTransferSearch(info, headers)).thenReturn(response);
        httpEntity = travelPlanController.getTransferResult(info, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetByCheapest(){
        TripInfo queryInfo = new TripInfo();
        Mockito.when(travelPlanService.getCheapest(queryInfo, headers)).thenReturn(response);
        httpEntity = travelPlanController.getByCheapest(queryInfo, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetByQuickest(){
        TripInfo queryInfo = new TripInfo();
        Mockito.when(travelPlanService.getQuickest(queryInfo, headers)).thenReturn(response);
        httpEntity = travelPlanController.getByQuickest(queryInfo, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetByMinStation(){
        TripInfo queryInfo = new TripInfo();
        Mockito.when(travelPlanService.getMinStation(queryInfo, headers)).thenReturn(response);
        httpEntity = travelPlanController.getByMinStation(queryInfo, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
