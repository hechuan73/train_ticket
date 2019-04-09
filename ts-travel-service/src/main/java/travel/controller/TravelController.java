package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel.entity.*;
import travel.service.TravelService;

import java.util.ArrayList;
import java.util.List;
import static org.springframework.http.ResponseEntity.ok;

/**
 * all respones ok() need user self to check null or not
 */
@RestController
@RequestMapping("/api/v1/travel")
public class TravelController {

    @Autowired
    private TravelService travelService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Travel Service ] !";
    }

    @GetMapping(value = "/train_types/{tripId}")
    public HttpEntity getTrainTypeByTripId(@PathVariable String tripId,
                                           @RequestHeader HttpHeaders headers) {
        TrainType trainType = travelService.getTrainTypeByTripId(tripId, headers);
        return ok(trainType);
    }

    @GetMapping(value = "/routes/{tripId}")
    public HttpEntity getRouteByTripId(@PathVariable String tripId,
                                       @RequestHeader HttpHeaders headers) {
        System.out.println("[Get Route By Trip ID] TripId:" + tripId);
        Route route = travelService.getRouteByTripId(tripId, headers);
        return ok(route);
    }

    @GetMapping(value = "/trips")
    public HttpEntity getTripsByRouteId(@RequestParam ArrayList<String> routeIds,
                                        @RequestHeader HttpHeaders headers) {
        ArrayList<ArrayList<Trip>> tripSet = travelService.getTripByRoute(routeIds, headers);
        return ok(tripSet);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trips")
    public HttpEntity createTrip(@RequestParam TravelInfo routeIds, @RequestHeader HttpHeaders headers) {
        return  new ResponseEntity(travelService.create(routeIds, headers), HttpStatus.CREATED);
    }

    //只返回Trip，不会返回票数信息
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/retrieve")
    public Trip retrieve(String tripId, @RequestHeader HttpHeaders headers) {
        return travelService.retrieve(tripId, headers);
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/trips")
    public HttpEntity updateTrip(@RequestBody TravelInfo info, @RequestHeader HttpHeaders headers) {
        return ok(travelService.update(info, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/trips")
    public HttpEntity deleteTrip(String tripId, @RequestHeader HttpHeaders headers) {
        return ok(travelService.delete(tripId, headers));
    }

    //返回Trip以及剩余票数
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/query")
    public HttpEntity queryInfo(@RequestBody Info info, @RequestHeader HttpHeaders headers) {
        if (info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null) {
            System.out.println("[Travel Service][Travel Query] Fail.Something null.");
            ArrayList<TripResponse> errorList = new ArrayList<>();
            return ok(errorList);
        }
        System.out.println("[Travel Service] Query TripResponse");
        ArrayList<TripResponse> trips = travelService.query(info, headers);
        return ok(trips);
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/queryWithPackage")
    public HttpEntity queryPackage(@RequestBody Info info, @RequestHeader HttpHeaders headers) {
        if (info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null) {
            System.out.println("[Travel Service][Travel Query] Fail.Something null.");
            return ok(null);
        }
        System.out.println("[Travel Service] Query TripResponse");
        ArrayList<TripResponse> responses = travelService.query(info, headers);
        return ok(responses);
    }

    //返回某一个Trip以及剩余票数
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trip_detail")
    public HttpEntity getTripAllDetailInfo(@RequestBody TripAllDetailInfo gtdi, @RequestHeader HttpHeaders headers) {
        TripAllDetail tripAllDetail = travelService.getTripAllDetailInfo(gtdi, headers);
        return ok(tripAllDetail);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        List<Trip> tripList = travelService.queryAll(headers);
        return ok(tripList);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/admin_trip")
    public HttpEntity adminQueryAll(@RequestHeader HttpHeaders headers) {
        ArrayList<AdminTrip> adminTripArrayList = travelService.adminQueryAll(headers);
        return ok(adminTripArrayList);
    }

}
