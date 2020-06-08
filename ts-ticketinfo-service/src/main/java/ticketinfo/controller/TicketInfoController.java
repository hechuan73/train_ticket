package ticketinfo.controller;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import ticketinfo.entity.Travel;
import ticketinfo.service.TicketInfoService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/ticketinfoservice")
@DefaultProperties(defaultFallback = "fallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
})
public class TicketInfoController {

    @Autowired
    TicketInfoService service;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ TicketInfo Service ] !";
    }

    @PostMapping(value = "/ticketinfo")
    @HystrixCommand
    public HttpEntity queryForTravel(@RequestBody Travel info, @RequestHeader HttpHeaders headers) {
        // TravelResult
        return ok(service.queryForTravel(info, headers));
    }

    @GetMapping(value = "/ticketinfo/{name}")
    @HystrixCommand
    public HttpEntity queryForStationId(@PathVariable String name, @RequestHeader HttpHeaders headers) {
        // String id
        return ok(service.queryForStationId(name, headers));
    }

    private HttpEntity fallback() {
        return ok(new Response<>());
    }
}
