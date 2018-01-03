package consign.controller;

import consign.domain.ConsignRecord;
import consign.domain.ConsignRequest;
import consign.domain.InsertConsignRecordResult;
import consign.service.ConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
public class ConsignController {
    @Autowired
    ConsignService service;

    @RequestMapping(value = "/consign/insertConsign", method= RequestMethod.POST)
    public InsertConsignRecordResult insertConsign(@RequestBody ConsignRequest request){
        return service.insertConsignRecord(request);
    }

    @RequestMapping(value = "/consign/updateConsign", method= RequestMethod.POST)
    public boolean updateConsign(@RequestBody ConsignRequest request){
        return service.updateConsignRecord(request);
    }

    @RequestMapping(value = "/consign/findByAccountId/{id}", method= RequestMethod.GET)
    public List<ConsignRecord> findByAccountId(@PathVariable String id){
        UUID newid = UUID.fromString(id);
        return service.queryByAccountId(newid);
    }

    @RequestMapping(value = "/consign/findByConsignee", method= RequestMethod.POST)
    public List<ConsignRecord> findByConsignee(@RequestParam(value = "consignee", required = true) String consignee){
        return service.queryByConsignee(consignee);
    }
}
