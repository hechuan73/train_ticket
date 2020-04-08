package adminorder.controller;

import edu.fudan.common.util.Response;
import foodsearch.controller.FoodController;
import foodsearch.entity.FoodOrder;
import foodsearch.service.FoodService;
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
public class FoodControllerTest {

    @InjectMocks
    private FoodController foodController;

    @Mock
    private FoodService foodService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = foodController.home();
        Assert.assertEquals("Welcome to [ Food Service ] !", result);
    }

    @Test
    public void testFindAllFoodOrder(){
        Mockito.when(foodService.findAllFoodOrder(headers)).thenReturn(response);
        httpEntity = foodController.findAllFoodOrder(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateFoodOrder(){
        FoodOrder addFoodOrder = new FoodOrder();
        Mockito.when(foodService.createFoodOrder(addFoodOrder, headers)).thenReturn(response);
        httpEntity = foodController.createFoodOrder(addFoodOrder, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateFoodOrder(){
        FoodOrder updateFoodOrder = new FoodOrder();
        Mockito.when(foodService.updateFoodOrder(updateFoodOrder, headers)).thenReturn(response);
        httpEntity = foodController.updateFoodOrder(updateFoodOrder, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteFoodOrder(){
        Mockito.when(foodService.deleteFoodOrder("orderId", headers)).thenReturn(response);
        httpEntity = foodController.deleteFoodOrder("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindFoodOrderByOrderId(){
        Mockito.when(foodService.findByOrderId("orderId", headers)).thenReturn(response);
        httpEntity = foodController.findFoodOrderByOrderId("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetAllFood(){
        Mockito.when(foodService.getAllFood("date", "startStation", "endStation", "tripId", headers)).thenReturn(response);
        httpEntity = foodController.getAllFood("date", "startStation", "endStation", "tripId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
