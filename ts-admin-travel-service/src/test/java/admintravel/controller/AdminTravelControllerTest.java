package admintravel.controller;

import admintravel.entity.TravelInfo;
import admintravel.service.AdminTravelService;
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
public class AdminTravelControllerTest {

    @InjectMocks
    private AdminTravelController adminTravelController;

    @Mock
    private AdminTravelService adminTravelService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = adminTravelController.home(headers);
        Assert.assertEquals("Welcome to [ AdminTravel Service ] !", result);
    }

    @Test
    public void testGetAllTravels(){
        Mockito.when(adminTravelService.getAllTravels(headers)).thenReturn(response);
        httpEntity = adminTravelController.getAllTravels(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddTravel(){
        TravelInfo request = new TravelInfo();
        Mockito.when(adminTravelService.addTravel(request, headers)).thenReturn(response);
        httpEntity = adminTravelController.addTravel(request, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateTravel(){
        TravelInfo request = new TravelInfo();
        Mockito.when(adminTravelService.updateTravel(request, headers)).thenReturn(response);
        httpEntity = adminTravelController.updateTravel(request, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteTravel(){
        Mockito.when(adminTravelService.deleteTravel("GaoTie", headers)).thenReturn(response);
        httpEntity = adminTravelController.deleteTravel("GaoTie", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
