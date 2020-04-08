package other.controller;

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
import other.entity.Order;
import other.entity.QueryInfo;
import other.entity.Seat;
import other.service.OrderOtherService;

import java.util.Date;

@RunWith(JUnit4.class)
public class OrderOtherControllerTest {

    @InjectMocks
    private OrderOtherController orderOtherController;

    @Mock
    private OrderOtherService orderService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = orderOtherController.home();
        Assert.assertEquals("Welcome to [ Order Other Service ] !", result);
    }

    @Test
    public void testGetTicketListByDateAndTripId(){
        Seat seatRequest = new Seat();
        Mockito.when(orderService.getSoldTickets(seatRequest, headers)).thenReturn(response);
        httpEntity = orderOtherController.getTicketListByDateAndTripId(seatRequest, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCreateNewOrder(){
        Order createOrder = new Order();
        Mockito.when(orderService.create(createOrder, headers)).thenReturn(response);
        httpEntity = orderOtherController.createNewOrder(createOrder, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddCreateNewOrder(){
        Order order = new Order();
        Mockito.when(orderService.addNewOrder(order, headers)).thenReturn(response);
        httpEntity = orderOtherController.addcreateNewOrder(order, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryOrders(){
        QueryInfo qi = new QueryInfo();
        Mockito.when(orderService.queryOrders(qi, qi.getLoginId(), headers)).thenReturn(response);
        httpEntity = orderOtherController.queryOrders(qi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryOrdersForRefresh(){
        QueryInfo qi = new QueryInfo();
        Mockito.when(orderService.queryOrdersForRefresh(qi, qi.getLoginId(), headers)).thenReturn(response);
        httpEntity = orderOtherController.queryOrdersForRefresh(qi, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testCalculateSoldTicket(){
        Date travelDate = new Date();
        Mockito.when(orderService.queryAlreadySoldOrders(travelDate, "trainNumber", headers)).thenReturn(response);
        httpEntity = orderOtherController.calculateSoldTicket(travelDate, "trainNumber", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetOrderPrice(){
        Mockito.when(orderService.getOrderPrice("orderId", headers)).thenReturn(response);
        httpEntity = orderOtherController.getOrderPrice("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testPayOrder(){
        Mockito.when(orderService.payOrder("orderId", headers)).thenReturn(response);
        httpEntity = orderOtherController.payOrder("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testGetOrderById(){
        Mockito.when(orderService.getOrderById("orderId", headers)).thenReturn(response);
        httpEntity = orderOtherController.getOrderById("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testModifyOrder(){
        Mockito.when(orderService.modifyOrder("orderId", 1, headers)).thenReturn(response);
        httpEntity = orderOtherController.modifyOrder("orderId", 1, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testSecurityInfoCheck(){
        Date checkDate = new Date();
        Mockito.when(orderService.checkSecurityAboutOrder(checkDate, "accountId", headers)).thenReturn(response);
        httpEntity = orderOtherController.securityInfoCheck(checkDate, "accountId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testSaveOrderInfo(){
        Order orderInfo = new Order();
        Mockito.when(orderService.saveChanges(orderInfo, headers)).thenReturn(response);
        httpEntity = orderOtherController.saveOrderInfo(orderInfo, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testUpdateOrder(){
        Order order = new Order();
        Mockito.when(orderService.updateOrder(order, headers)).thenReturn(response);
        httpEntity = orderOtherController.updateOrder(order, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteOrder(){
        Mockito.when(orderService.deleteOrder("orderId", headers)).thenReturn(response);
        httpEntity = orderOtherController.deleteOrder("orderId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testFindAllOrder(){
        Mockito.when(orderService.getAllOrders(headers)).thenReturn(response);
        httpEntity = orderOtherController.findAllOrder(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
