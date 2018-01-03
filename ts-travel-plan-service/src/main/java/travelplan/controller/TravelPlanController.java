package travelplan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import travelplan.domain.QueryInfo;
import travelplan.domain.TransferTravelSearchInfo;
import travelplan.domain.TransferTravelSearchResult;
import travelplan.domain.TravelAdvanceResult;
import travelplan.service.TravelPlanService;

@RestController
public class TravelPlanController {

    @Autowired
    TravelPlanService travelPlanService;

    @RequestMapping(value="/travelPlan/getTransferResult", method= RequestMethod.POST)
    public TransferTravelSearchResult getTransferResult(@RequestBody TransferTravelSearchInfo info) {
        System.out.println("[Search Transit]");
        return travelPlanService.getTransferSearch(info);
    }

    @RequestMapping(value="/travelPlan/getCheapest", method= RequestMethod.POST)
    public TravelAdvanceResult getByCheapest(@RequestBody QueryInfo queryInfo) {
        System.out.println("[Search Cheapest]");
        return travelPlanService.getCheapest(queryInfo);
    }

    @RequestMapping(value="/travelPlan/getQuickest", method= RequestMethod.POST)
    public TravelAdvanceResult getByQuickest(@RequestBody QueryInfo queryInfo) {
        System.out.println("[Search Quickest]");
        return travelPlanService.getQuickest(queryInfo);
    }

    @RequestMapping(value="/travelPlan/getMinStation", method= RequestMethod.POST)
    public TravelAdvanceResult getByMinStation(@RequestBody QueryInfo queryInfo) {
        System.out.println("[Search Min Station]");
        return travelPlanService.getMinStation(queryInfo);
    }


}
