package route.controller;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.dsl.http.Http;
import org.springframework.web.bind.annotation.*;
import route.entity.*;
import route.service.RouteService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/routeservice")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Route Service ] !";
    }

    @PostMapping(path = "/routes")
    public ResponseEntity<Response> createAndModifyRoute(@RequestBody RouteInfo createAndModifyRouteInfo, @RequestHeader HttpHeaders headers) {
        Route route = routeService.createAndModify(createAndModifyRouteInfo, headers);
        if (route == null) {
            return ok(new Response(0, "Station Number Not Equal To Distance Number", createAndModifyRouteInfo));
        } else {
            return new ResponseEntity<>(new Response(1, "", route), HttpStatus.CREATED);
        }
    }

    @DeleteMapping(path = "/routes/{routeId}")
    public HttpEntity deleteRoute(@PathVariable String routeId, @RequestHeader HttpHeaders headers) {
        boolean isDeleted = routeService.deleteRoute(routeId, headers);
        if (isDeleted) {
            return ok(new Response(1, "Delete Success", routeId));
        } else {
            return ok(new Response(0, "Delete failed, Reason unKnown with this routeId", routeId));
        }
    }

    @GetMapping(path = "/routes/{routeId}")
    public HttpEntity queryById(@PathVariable String routeId, @RequestHeader HttpHeaders headers) {
        Route route = routeService.getRouteById(routeId, headers);
        if (route == null) {
            return ok(new Response(0, "No content with the routeId", routeId));
        } else {
            return ok(new Response(1, "Success", route));
        }
    }

    @GetMapping(path = "/routes")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        List<Route> routes = routeService.getAllRoutes(headers);
        if (routes != null && routes.size() > 0) {
            return ok(new Response(1, "Success", routes));
        } else {
            return ok(new Response(0, "No Content", routes));
        }
    }

    @GetMapping(path = "/routes/{startId}/{terminalId}")
    public HttpEntity queryByStartAndTerminal(@PathVariable String startId,
                                              @PathVariable String terminalId,
                                              @RequestHeader HttpHeaders headers) {
        List<Route> routes = routeService.getRouteByStartAndTerminal(startId, terminalId, headers);
        if (routes != null && routes.size() > 0) {
            return ok(new Response(1, "Success", routes));
        } else {
            return ok(new Response(0, "No routes with the startId and terminalId", startId + " -- " + terminalId));
        }
    }

}