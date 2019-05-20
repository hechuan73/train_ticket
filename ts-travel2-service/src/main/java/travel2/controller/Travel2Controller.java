package travel2.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import travel2.entity.*;
import travel2.service.Travel2Service;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/travel2service")
public class Travel2Controller {

    @Autowired
    private Travel2Service service;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Travle2 Service ] !";
    }

    @GetMapping(value = "/train_types/{tripId}")
    public HttpEntity getTrainTypeByTripId(@PathVariable String tripId,
                                           @RequestHeader HttpHeaders headers) {
        // TrainType
        return ok(service.getTrainTypeByTripId(tripId, headers));
    }

    @GetMapping(value = "/routes/{tripId}")
    public HttpEntity getRouteByTripId(@PathVariable String tripId,
                                       @RequestHeader HttpHeaders headers) {
        System.out.println("[Get Route By Trip ID] TripId:" + tripId);
        //Route
        return ok(service.getRouteByTripId(tripId, headers));
    }

    @PostMapping(value = "/trips/routes")
    public HttpEntity getTripsByRouteId(@RequestBody ArrayList<String> routeIds,
                                        @RequestHeader HttpHeaders headers) {
        // ArrayList<ArrayList<Trip>>
        return ok(service.getTripByRoute(routeIds, headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trips")
    public HttpEntity<?> createTrip(@RequestBody TravelInfo routeIds, @RequestHeader HttpHeaders headers) {
        // null
        return new ResponseEntity<>(service.create(routeIds, headers), HttpStatus.CREATED);
    }

    //只返回Trip，不会返回票数信息
    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips/{tripId}")
    public HttpEntity retrieve(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        // Trip
        return ok(service.retrieve(tripId, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/trips")
    public HttpEntity updateTrip(@RequestBody TravelInfo info, @RequestHeader HttpHeaders headers) {
        // Trip
        return ok(service.update(info, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/trips/{tripId}")
    public HttpEntity deleteTrip(@PathVariable String tripId, @RequestHeader HttpHeaders headers) {
        // string
        return ok(service.delete(tripId, headers));
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
        return ok(service.query(info, headers));
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(value = "/travel2/queryWithPackage", method = RequestMethod.POST)
//    public QueryTripResponsePackage queryPackage(@RequestBody TripInfo info, @RequestHeader HttpHeaders headers) {
//        if (info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
//                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
//                info.getDepartureTime() == null) {
//            System.out.println("[Travel Other Service][Travel Query] Fail.Something null.");
//            ArrayList<TripResponse> errorList = new ArrayList<>();
//            return new QueryTripResponsePackage(false, "Fail.", errorList);
//        }
//        System.out.println("[Travel Other Servicee] Query TripResponse");
//        ArrayList<TripResponse> responses = service.query(info, headers);
//        return new QueryTripResponsePackage(true, "Success.", responses);
//    }

    //返回某一个Trip以及剩余票数
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/trip_detail")
    public HttpEntity getTripAllDetailInfo(@RequestBody TripAllDetailInfo gtdi, @RequestHeader HttpHeaders headers) {
        // TripAllDetailInfo
        // TripAllDetail tripAllDetail
        return ok(service.getTripAllDetailInfo(gtdi, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/trips")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        // List<Trip>
        return ok(service.queryAll(headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/admin_trip")
    public HttpEntity adminQueryAll(@RequestHeader HttpHeaders headers) {
        // ArrayList<AdminTrip>
        return ok(service.adminQueryAll(headers));
    }

}
