package admintravel.controller;

import admintravel.entity.TravelInfo;
import admintravel.service.AdminTravelService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/admintravelservice")
@DefaultProperties(defaultFallback = "fallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "5000")
})
public class AdminTravelController {
    @Autowired
    AdminTravelService adminTravelService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminTravel Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/admintravel")
    @HystrixCommand
    public HttpEntity getAllTravels(@RequestHeader HttpHeaders headers) {
        return ok(adminTravelService.getAllTravels(headers));
    }

    @PostMapping(value = "/admintravel")
    @HystrixCommand
    public HttpEntity addTravel(@RequestBody TravelInfo request, @RequestHeader HttpHeaders headers) {
        return ok(adminTravelService.addTravel(request, headers));
    }

    @PutMapping(value = "/admintravel")
    @HystrixCommand
    public HttpEntity updateTravel(@RequestBody TravelInfo request, @RequestHeader HttpHeaders headers) {
        return ok(adminTravelService.updateTravel(request, headers));
    }

    @DeleteMapping(value = "/admintravel/{tripId}")
    @HystrixCommand
    public HttpEntity deleteTravel(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        return ok(adminTravelService.deleteTravel(tripId, headers));
    }


    private HttpEntity fallback() {
        return ok(new Response<>(0, null, null));
    }
}
