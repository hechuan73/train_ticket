package travelplan.controller;

import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import edu.fudan.common.util.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import travelplan.entity.TripInfo;
import travelplan.entity.TransferTravelInfo;
import travelplan.service.TravelPlanService;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("api/v1/travelplanservice")
@DefaultProperties(defaultFallback = "fallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
})
public class TravelPlanController {

    @Autowired
    TravelPlanService travelPlanService;

    private static final Logger LOGGER = LoggerFactory.getLogger(TravelPlanController.class);

    @GetMapping(path = "/welcome" )
    public String home() {
        return "Welcome to [ TravelPlan Service ] !";
    }

    @PostMapping(value="/travelPlan/transferResult" )
    @HystrixCommand
    public HttpEntity getTransferResult(@RequestBody TransferTravelInfo info, @RequestHeader HttpHeaders headers) {
        TravelPlanController.LOGGER.info("[Search Transit]");
        return ok(travelPlanService.getTransferSearch(info, headers));
    }

    @PostMapping(value="/travelPlan/cheapest")
    @HystrixCommand
    public HttpEntity getByCheapest(@RequestBody TripInfo queryInfo, @RequestHeader HttpHeaders headers) {
        TravelPlanController.LOGGER.info("[Search Cheapest]");
        return ok(travelPlanService.getCheapest(queryInfo, headers));
    }

    @PostMapping(value="/travelPlan/quickest")
    @HystrixCommand
    public HttpEntity getByQuickest(@RequestBody TripInfo queryInfo, @RequestHeader HttpHeaders headers) {
        TravelPlanController.LOGGER.info("[Search Quickest]");
        return ok(travelPlanService.getQuickest(queryInfo, headers));
    }

    @PostMapping(value="/travelPlan/minStation")
    @HystrixCommand
    public HttpEntity getByMinStation(@RequestBody TripInfo queryInfo, @RequestHeader HttpHeaders headers) {
        TravelPlanController.LOGGER.info("[Search Min Station]");
        return ok(travelPlanService.getMinStation(queryInfo, headers));
    }


    private HttpEntity fallback() {
        return ok(new Response<>(0, null, null));
    }
}
