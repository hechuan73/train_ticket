package consign.controller;

import consign.entity.Consign;
import consign.service.ConsignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/consignservice")
public class ConsignController {

    @Autowired
    ConsignService service;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Consign Service ] !";
    }


//    {
//        "accountId": "f150e530-9142-4cd8-80f1-695693dc704f",
//            "consignee": "string",
//            "from": "string",
//            "handleDate": "Fri Apr 12 13:29:40 CST 2019",
//            "id": "f150e530-9142-4cd8-80f1-695693dc704f",
//            "phone": "string",
//            "targetDate": "Fri Apr 12 13:29:40 CST 2019",
//            "to": "string",
//            "weight": 12.3,
//            "within": true
//    }
//
    @PostMapping(value = "/consigns")
    public HttpEntity insertConsign(@RequestBody Consign request,
                                    @RequestHeader HttpHeaders headers) {
        return ok(service.insertConsignRecord(request, headers));
    }

    @PutMapping(value = "/consigns")
    public HttpEntity updateConsign(@RequestBody Consign request, @RequestHeader HttpHeaders headers) {
        return ok(service.updateConsignRecord(request, headers));
    }

    @GetMapping(value = "/consigns/account/{id}")
    public HttpEntity findByAccountId(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        UUID newid = UUID.fromString(id);
        return ok(service.queryByAccountId(newid, headers));
    }

    @GetMapping(value = "/consigns/{consignee}")
    public HttpEntity findByConsignee(@PathVariable String consignee, @RequestHeader HttpHeaders headers) {
        return ok(service.queryByConsignee(consignee, headers));
    }
}
