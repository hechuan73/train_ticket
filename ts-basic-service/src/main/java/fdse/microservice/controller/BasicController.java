package fdse.microservice.controller;

import fdse.microservice.domain.QueryForStationId;
import fdse.microservice.domain.QueryForTravel;
import fdse.microservice.domain.QueryStation;
import fdse.microservice.domain.ResultForTravel;
import fdse.microservice.service.BasicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
@RestController
public class BasicController {

    @Autowired
    BasicService service;

    @RequestMapping(value="/basic/queryForTravel", method= RequestMethod.POST)
    public ResultForTravel queryForTravel(@RequestBody QueryForTravel info){
        return service.queryForTravel(info);
    }

    @RequestMapping(value="/basic/queryForStationId", method= RequestMethod.POST)
    public String queryForStationId(@RequestBody QueryStation info){
        return service.queryForStationId(info);
    }
}
