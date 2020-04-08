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

@RunWith(JUnit4.class)
public class TrainFoodControllerTest {

    @InjectMocks
    private TrainFoodController trainFoodController;

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
        String result = trainFoodController.home();
        Assert.assertEquals("Welcome to [ Train Food Service ] !", result);
    }

    @Test
    public void testGetAllTrainFood(){
        Mockito.when(foodMapService.listTrainFood(headers)).thenReturn(response);
        httpEntity = trainFoodController.getAllTrainFood(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetTrainFoodOfTrip(){
        Mockito.when(foodMapService.listTrainFoodByTripId("tripId", headers)).thenReturn(response);
        httpEntity = trainFoodController.getTrainFoodOfTrip("tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
