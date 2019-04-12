package food.controller;

import edu.fudan.common.util.Response;
import food.entity.FoodStore;
import food.service.FoodMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/foodmapservice")
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
        if (foodStoreList != null && foodStoreList.size() > 0) {
            return ok(new Response(1, "Success", foodStoreList));
        } else {
            return ok(new Response(0, "Foodstore is empty", null));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping("/foodstores/{stationId}")
    public HttpEntity getFoodStoresOfStation(@PathVariable String stationId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Map Service][Get FoodStores By StationId]");
        List<FoodStore> foodStoreList = foodMapService.listFoodStoresByStationId(stationId, headers);
        if (foodStoreList != null && foodStoreList.size() > 0) {
            return ok(new Response(1, "Success", foodStoreList));
        } else {
            return ok(new Response(0, "FoodStore is empty", null));
        }
    }

    @CrossOrigin(origins = "*")
    @PostMapping("/foodstores")
    public HttpEntity getFoodStoresByStationIds(@RequestBody List<String> stationIdList) {
        List<FoodStore> foodStoreList = foodMapService.getFoodStoresByStationIds(stationIdList);
        if (foodStoreList != null) {
            return ok(new Response(1, "Success", foodStoreList));
        } else {
            return ok(new Response(0, "No content", foodStoreList));
        }
    }
}
