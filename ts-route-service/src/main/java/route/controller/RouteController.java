package route.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import route.domain.*;
import route.service.RouteService;

@RestController
public class RouteController {

    @Autowired
    private RouteService routeService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Route Service ] !";
    }

    @RequestMapping(path = "/route/createAndModify", method = RequestMethod.POST)
    public CreateAndModifyRouteResult createAndModifyRoute(@RequestBody CreateAndModifyRouteInfo createAndModifyRouteInfo){
        return routeService.createAndModify(createAndModifyRouteInfo);
    }

    @RequestMapping(path = "/route/delete", method = RequestMethod.POST)
    public DeleteRouteResult deleteRoute(@RequestBody DeleteRouteInfo deleteRouteInfo){
        return routeService.deleteRoute(deleteRouteInfo);
    }

    @RequestMapping(path = "/route/queryById/{routeId}", method = RequestMethod.GET)
    public GetRouteByIdResult queryById(@PathVariable String routeId){
        return routeService.getRouteById(routeId);
    }

    @RequestMapping(path = "/route/queryAll", method = RequestMethod.GET)
    public GetRoutesListlResult queryAll(){
        return routeService.getAllRoutes();
    }

    @RequestMapping(path = "/route/queryByStartAndTerminal", method = RequestMethod.POST)
    public GetRoutesListlResult queryByStartAndTerminal(@RequestBody GetRouteByStartAndTerminalInfo getRouteByStartAndTerminalInfo){
        return routeService.getRouteByStartAndTerminal(getRouteByStartAndTerminalInfo);
    }

}