package route.controller;

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
import route.entity.RouteInfo;
import route.service.RouteService;

@RunWith(JUnit4.class)
public class RouteControllerTest {

    @InjectMocks
    private RouteController routeController;

    @Mock
    private RouteService routeService;

    private HttpHeaders headers = new HttpHeaders();
    private HttpEntity httpEntity = new HttpEntity(headers);
    private Response response = new Response();

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testHome(){
        Assert.assertEquals("Welcome to [ Route Service ] !", routeController.home());
    }

    @Test
    public void testCreateAndModifyRoute(){
        RouteInfo createAndModifyRouteInfo = new RouteInfo();
        Mockito.when(routeService.createAndModify(createAndModifyRouteInfo, headers)).thenReturn(response);
        httpEntity = routeController.createAndModifyRoute(createAndModifyRouteInfo, headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testDeleteRoute(){
        Mockito.when(routeService.deleteRoute("routeId", headers)).thenReturn(response);
        httpEntity = routeController.deleteRoute("routeId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryById(){
        Mockito.when(routeService.getRouteById("routeId", headers)).thenReturn(response);
        httpEntity = routeController.queryById("routeId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryAll(){
        Mockito.when(routeService.getAllRoutes(headers)).thenReturn(response);
        httpEntity = routeController.queryAll(headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

    @Test
    public void testQueryByStartAndTerminal(){
        Mockito.when(routeService.getRouteByStartAndTerminal("startId", "terminalId", headers)).thenReturn(response);
        httpEntity = routeController.queryByStartAndTerminal("startId", "terminalId", headers);
        Assert.assertEquals(new ResponseEntity<>(response, HttpStatus.OK), httpEntity);
    }

}
