package adminorder.controller;

import adminorder.entity.Order;
import adminorder.service.AdminOrderService;
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
public class AdminOrderControllerTest {

    @InjectMocks
    private AdminOrderController adminOrderController;

    @Mock
    private AdminOrderService adminOrderService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = adminOrderController.home(headers);
        Assert.assertEquals("Welcome to [ AdminOrder Service ] !", result);
    }

    @Test
    public void testGetAllOrders(){
        Mockito.when(adminOrderService.getAllOrders(headers)).thenReturn(response);
        httpEntity = adminOrderController.getAllOrders(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddOrder(){
        Order order = new Order(null, null, null, null, null, null, 0, null, "G", 0, 0, null, null, null, 0, null);
        Mockito.when(adminOrderService.addOrder(order, headers)).thenReturn(response);
        httpEntity = adminOrderController.addOrder(order, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateOrder(){
        Order order = new Order(null, null, null, null, null, null, 0, null, "G", 0, 0, null, null, null, 0, null);
        Mockito.when(adminOrderService.updateOrder(order, headers)).thenReturn(response);
        httpEntity = adminOrderController.updateOrder(order, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteOrder(){
        Mockito.when(adminOrderService.deleteOrder("orderId", "G", headers)).thenReturn(response);
        httpEntity = adminOrderController.deleteOrder("orderId", "G", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }
}
