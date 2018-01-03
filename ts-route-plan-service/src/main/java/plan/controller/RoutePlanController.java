package plan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import plan.domain.GetRoutePlanInfo;
import plan.domain.RoutePlanResults;
import plan.domain.TripResponse;
import plan.service.RoutePlanService;

import java.util.ArrayList;

@RestController
public class RoutePlanController {

    @Autowired
    private RoutePlanService routePlanService;

    @RequestMapping(value = "/routePlan/cheapestRoute", method = RequestMethod.POST)
    public RoutePlanResults getCheapestRoutes(@RequestBody GetRoutePlanInfo info){
        System.out.println("[Route Plan Service][Get Cheapest Routes] From:" + info.getFormStationName() +
            " to:" + info.getToStationName() + " Num:" + info.getNum() + " Date:" + info.getTravelDate());
        return routePlanService.searchCheapestResult(info);
    }

    @RequestMapping(value = "/routePlan/quickestRoute", method = RequestMethod.POST)
    public RoutePlanResults getQuickestRoutes(@RequestBody GetRoutePlanInfo info){
        System.out.println("[Route Plan Service][Get Quickest Routes] From:" + info.getFormStationName() +
                " to:" + info.getToStationName() + " Num:" + info.getNum() + " Date:" + info.getTravelDate());
        return routePlanService.searchQuickestResult(info);
    }

    @RequestMapping(value = "/routePlan/minStopStations", method = RequestMethod.POST)
    public RoutePlanResults getMinStopStations(@RequestBody GetRoutePlanInfo info){
        System.out.println("[Route Plan Service][Get Min Stop Stations] From:" + info.getFormStationName() +
                " to:" + info.getToStationName() + " Num:" + info.getNum() + " Date:" + info.getTravelDate());
        return routePlanService.searchMinStopStations(info);
    }
}
