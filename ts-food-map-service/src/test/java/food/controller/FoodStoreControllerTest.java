package food.controller;

import edu.fudan.common.util.Response;
import food.service.FoodMapService;
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
public class FoodStoreControllerTest {

    @InjectMocks
    private FoodStoreController foodStoreController;

    @Mock
    private FoodMapService foodMapService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = foodStoreController.home();
        Assert.assertEquals("Welcome to [ Food store Service ] !", result);
    }

    @Test
    public void testGetAllFoodStores(){
        Mockito.when(foodMapService.listFoodStores(headers)).thenReturn(response);
        httpEntity = foodStoreController.getAllFoodStores(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }


    @Test
    public void testGetFoodStoresOfStation(){
        Mockito.when(foodMapService.listFoodStoresByStationId("stationId", headers)).thenReturn(response);
        httpEntity = foodStoreController.getFoodStoresOfStation("stationId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetFoodStoresByStationIds(){
        List<String> stationIdList = new ArrayList<>();
        Mockito.when(foodMapService.getFoodStoresByStationIds(stationIdList)).thenReturn(response);
        httpEntity = foodStoreController.getFoodStoresByStationIds(stationIdList);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
