package adminroute.controller;

import adminroute.entity.RouteInfo;
import adminroute.service.AdminRouteService;
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
public class AdminRouteControllerTest {

    @InjectMocks
    private AdminRouteController adminRouteController;

    @Mock
    private AdminRouteService adminRouteService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        String result = adminRouteController.home(headers);
        Assert.assertEquals("Welcome to [ AdminRoute Service ] !", result);
    }

    @Test
    public void testGetAllRoutes(){
        Mockito.when(adminRouteService.getAllRoutes(headers)).thenReturn(response);
        httpEntity = adminRouteController.getAllRoutes(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testAddRoute(){
        RouteInfo request = new RouteInfo();
        Mockito.when(adminRouteService.createAndModifyRoute(request, headers)).thenReturn(response);
        httpEntity = adminRouteController.addRoute(request, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteRoute(){
        Mockito.when(adminRouteService.deleteRoute("routeId", headers)).thenReturn(response);
        httpEntity = adminRouteController.deleteRoute("routeId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
