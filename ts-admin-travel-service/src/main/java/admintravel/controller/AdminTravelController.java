package admintravel.controller;

import admintravel.domain.request.AddAndModifyTravelRequest;
import admintravel.domain.request.DeleteTravelRequest;
import admintravel.domain.response.AdminFindAllResult;
import admintravel.domain.response.ResponseBean;
import admintravel.service.AdminTravelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminTravelController {
    @Autowired
    AdminTravelService adminTravelService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/admintravel/findAll/{id}", method = RequestMethod.GET)
    public AdminFindAllResult getAllTravels(@PathVariable String id){
        return adminTravelService.getAllTravels(id);
    }

    @RequestMapping(value = "/admintravel/addTravel", method= RequestMethod.POST)
    public ResponseBean addTravel(@RequestBody AddAndModifyTravelRequest request){
        return adminTravelService.addTravel(request);
    }

    @RequestMapping(value = "/admintravel/updateTravel", method= RequestMethod.POST)
    public ResponseBean updateTravel(@RequestBody AddAndModifyTravelRequest request){
        return adminTravelService.updateTravel(request);
    }

    @RequestMapping(value = "/admintravel/deleteTravel", method= RequestMethod.POST)
    public ResponseBean deleteTravel(@RequestBody DeleteTravelRequest request){
        return adminTravelService.deleteTravel(request);
    }
}
