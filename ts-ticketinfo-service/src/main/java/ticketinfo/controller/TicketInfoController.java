package ticketinfo.controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ticketinfo.domain.QueryForStationId;
import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;
import ticketinfo.service.TicketInfoService;

@RestController
public class TicketInfoController {

    @Autowired
    TicketInfoService service;

    @RequestMapping(value="/ticketinfo/queryForTravel", method = RequestMethod.POST)
    public ResultForTravel queryForTravel(@RequestBody QueryForTravel info){
        return service.queryForTravel(info);
    }

    @RequestMapping(value="/ticketinfo/queryForStationId", method = RequestMethod.POST)
    public String queryForStationId(@RequestBody QueryForStationId info){
        return service.queryForStationId(info);
    }
}
