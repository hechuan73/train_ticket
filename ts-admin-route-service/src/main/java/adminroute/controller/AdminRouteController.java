package adminroute.controller;

import adminroute.domain.request.CreateAndModifyRouteRequest;
import adminroute.domain.request.DeleteRouteRequest;
import adminroute.domain.response.CreateAndModifyRouteResult;
import adminroute.domain.response.DeleteRouteResult;
import adminroute.domain.response.GetRoutesListlResult;
import adminroute.service.AdminRouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminRouteController {
    @Autowired
    AdminRouteService adminRouteService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminroute/findAll/{id}", method = RequestMethod.GET)
    public GetRoutesListlResult getAllRoutes(@PathVariable String id){
        return adminRouteService.getAllRoutes(id);
    }

    @RequestMapping(value = "/adminroute/createAndModifyRoute", method= RequestMethod.POST)
    public CreateAndModifyRouteResult addRoute(@RequestBody CreateAndModifyRouteRequest request){
        return adminRouteService.createAndModifyRoute(request);
    }

    @RequestMapping(value = "/adminroute/deleteRoute", method= RequestMethod.POST)
    public DeleteRouteResult deleteRoute(@RequestBody DeleteRouteRequest request){
        return adminRouteService.deleteRoute(request);
    }
}
