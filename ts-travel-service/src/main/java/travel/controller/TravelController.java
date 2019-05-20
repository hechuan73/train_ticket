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

import static org.springframework.http.ResponseEntity.ok;


@RestController
@RequestMapping("/api/v1/travelservice")
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
        // TrainType
        return ok(travelService.getTrainTypeByTripId(tripId, headers));
    }

    @GetMapping(value = "/routes/{tripId}")
    public HttpEntity getRouteByTripId(@PathVariable String tripId,
                                       @RequestHeader HttpHeaders headers) {
        System.out.println("[Get Route By Trip ID] TripId:" + tripId);
        //Route
        return ok(travelService.getRouteByTripId(tripId, headers));
    }

    @PostMapping(value = "/trips/routes")
    public HttpEntity getTripsByRouteId(@RequestBody ArrayList<String> routeIds,
                                        @RequestHeader HttpHeaders headers) {
        // ArrayList<ArrayList<Trip>>
        return ok(travelService.getTripByRoute(routeIds, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trips")
    public HttpEntity<?> createTrip(@RequestBody TravelInfo routeIds, @RequestHeader HttpHeaders headers) {
        // null
        return new ResponseEntity<>(travelService.create(routeIds, headers), HttpStatus.CREATED);
    }

    //只返回Trip，不会返回票数信息
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips/{tripId}")
    public HttpEntity retrieve(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        // Trip
        return ok(travelService.retrieve(tripId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/trips")
    public HttpEntity updateTrip(@RequestBody TravelInfo info, @RequestHeader HttpHeaders headers) {
        // Trip
        return ok(travelService.update(info, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/trips/{tripId}")
    public HttpEntity deleteTrip(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        // string
        return ok(travelService.delete(tripId, headers));
    }

    //返回Trip以及剩余票数
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trips/left")
    public HttpEntity queryInfo(@RequestBody TripInfo info, @RequestHeader HttpHeaders headers) {
        if (info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null) {
            System.out.println("[Travel Service][Travel Query] Fail.Something null.");
            ArrayList<TripResponse> errorList = new ArrayList<>();
            return ok(errorList);
        }
        System.out.println("[Travel Service] Query TripResponse");
        //ArrayList<TripResponse>  ;
        return ok(travelService.query(info, headers));
    }

//    @CrossOrigin(origins = "*")
//    @PostMapping(value = "/package")
//    public HttpEntity queryPackage(@RequestBody TripInfo info, @RequestHeader HttpHeaders headers) {
//        if (info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
//                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
//                info.getDepartureTime() == null) {
//            System.out.println("[Travel Service][Travel Query] Fail.Something null.");
//            return ok(new Response<>(0, "Fail.Something null.", null));
//        }
//        System.out.println("[Travel Service] Query TripResponse");
//        //ArrayList<TripResponse>
//        return ok(travelService.query(info, headers));
//    }

    //返回某一个Trip以及剩余票数
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trip_detail")
    public HttpEntity getTripAllDetailInfo(@RequestBody TripAllDetailInfo gtdi, @RequestHeader HttpHeaders headers) {
        // TripAllDetailInfo
        // TripAllDetail tripAllDetail
        return ok(travelService.getTripAllDetailInfo(gtdi, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        // List<Trip>
        return ok(travelService.queryAll(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/admin_trip")
    public HttpEntity adminQueryAll(@RequestHeader HttpHeaders headers) {
        // ArrayList<AdminTrip>
        return ok(travelService.adminQueryAll(headers));
    }

}
