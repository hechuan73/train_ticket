package food.controller;

import food.entity.FoodStore;
import food.service.FoodMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/foodmap")
public class FoodStoreController {

    @Autowired
    FoodMapService foodMapService;

    @GetMapping(path = "/foodstores/welcome")
    public String home() {
        return "Welcome to [ Food store Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores")
    public HttpEntity getAllFoodStores(@RequestHeader HttpHeaders headers) {
        System.out.println("[Food Map Service][Get All FoodStores]");
        List<FoodStore> foodStoreList = foodMapService.listFoodStores(headers);
        return ok(foodStoreList);
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores/{stationId}")
    public HttpEntity getFoodStoresOfStation(@PathVariable String stationId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Map Service][Get FoodStores By StationId]");
        List<FoodStore> foodStoreList = foodMapService.listFoodStoresByStationId(stationId, headers);

        return ok(foodStoreList);
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/foodstores")
    public HttpEntity getFoodStoresByStationIds(@RequestBody List<String> stationIdList) {
        List<FoodStore> foodStoreList = foodMapService.getFoodStoresByStationIds(stationIdList);
        return ok(foodStoreList);
    }


}
