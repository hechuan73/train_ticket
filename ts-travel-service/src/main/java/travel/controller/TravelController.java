package travel.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import travel.domain.*;
import travel.service.TravelService;
import java.util.ArrayList;
import java.util.List;

@RestController
public class TravelController {

    @Autowired
    private TravelService travelService;

    @RequestMapping(value="/travel/getTrainTypeByTripId/{tripId}", method = RequestMethod.GET)
    public GetTrainTypeResult getTrainTypeByTripId(@PathVariable String tripId){
        return travelService.getTrainTypeByTripId(tripId);
    }

    @RequestMapping(value = "/travel/getRouteByTripId/{tripId}", method = RequestMethod.GET)
    public GetRouteResult getRouteByTripId(@PathVariable String tripId){
        System.out.println("[Get Route By Trip ID] TripId:" + tripId);
        return travelService.getRouteByTripId(tripId);
    }

    @RequestMapping(value = "/travel/getTripsByRouteId", method = RequestMethod.POST)
    public GetTripsByRouteIdResult getTripsByRouteId(@RequestBody GetTripsByRouteIdInfo info){
        return travelService.getTripByRoute(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/create", method= RequestMethod.POST)
    public String create(@RequestBody Information info){
        return travelService.create(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/retrieve", method= RequestMethod.POST)
    public Trip retrieve(@RequestBody Information2 info){
        return travelService.retrieve(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/update", method= RequestMethod.POST)
    public String update(@RequestBody Information info){
        return travelService.update(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/delete", method= RequestMethod.POST)
    public String delete(@RequestBody Information2 info){
        return travelService.delete(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/query", method= RequestMethod.POST)
    public ArrayList<TripResponse> query(@RequestBody QueryInfo info){
        if(info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null){
            System.out.println("[Travel Service][Travel Query] Fail.Something null.");
            ArrayList<TripResponse> errorList = new ArrayList<>();
            return errorList;
        }
        System.out.println("[Travel Service] Query TripResponse");
        return travelService.query(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/queryWithPackage", method= RequestMethod.POST)
    public QueryTripResponsePackage queryPackage(@RequestBody QueryInfo info){
        if(info.getStartingPlace() == null || info.getStartingPlace().length() == 0 ||
                info.getEndPlace() == null || info.getEndPlace().length() == 0 ||
                info.getDepartureTime() == null){
            System.out.println("[Travel Service][Travel Query] Fail.Something null.");
            ArrayList<TripResponse> errorList = new ArrayList<>();
            return new QueryTripResponsePackage(false,"Fail.",errorList);
        }
        System.out.println("[Travel Service] Query TripResponse");
        ArrayList<TripResponse> responses = travelService.query(info);
        return new QueryTripResponsePackage(true,"Success.",responses);
    }


    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/getTripAllDetailInfo", method= RequestMethod.POST)
    public GetTripAllDetailResult getTripAllDetailInfo(@RequestBody GetTripAllDetailInfo gtdi){
        return travelService.getTripAllDetailInfo(gtdi);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/queryAll", method= RequestMethod.GET)
    public List<Trip> queryAll(){
        return travelService.queryAll();
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/travel/adminQueryAll", method= RequestMethod.GET)
    public AdminFindAllResult adminQueryAll(){
        return travelService.adminQueryAll();
    }
}
