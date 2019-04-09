package foodsearch.controller;

import foodsearch.entity.*;
import foodsearch.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/food")
public class FoodController {

    @Autowired
    FoodService foodService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Food Service ] !";
    }

    // get
    @GetMapping(path = "/orders")
    public HttpEntity findAllFoodOrder(@RequestHeader HttpHeaders headers) {
        System.out.println("[Food Service]Try to Find all FoodOrder!");
        return ok(foodService.findAllFoodOrder(headers));
    }

    // add
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/orders")
    public HttpEntity<?> createFoodOrder(@RequestBody FoodOrder addFoodOrder, @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Service]Try to Create a FoodOrder!");
        FoodOrder foodOrder = foodService.createFoodOrder(addFoodOrder, headers);
        return new ResponseEntity<>(foodOrder, HttpStatus.CREATED);
    }

    // update
    @PatchMapping(path = "/orders")
    public HttpEntity<?> updateFoodOrder(@RequestBody FoodOrder updateFoodOrder, @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Service]Try to Update a FoodOrder!");
        FoodOrder foodOrder = foodService.updateFoodOrder(updateFoodOrder, headers);
        return new ResponseEntity<>(foodOrder, HttpStatus.ACCEPTED);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping(path = "/orders/{orderId}")
    public HttpEntity<?> deleteFoodOrder(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Service]Try to Cancel a FoodOrder!");
        FoodOrder foodOrder = foodService.deleteFoodOrder(orderId, headers);
        return new ResponseEntity<>(foodOrder, HttpStatus.CREATED);
    }

    @GetMapping(path = "/orders/{orderId}")
    public HttpEntity findFoodOrderByOrderId(@PathVariable String orderId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Service]Try to Find all FoodOrder!");
        FoodOrder foodOrder = foodService.findByOrderId(orderId, headers);
        return ok(foodOrder);
    }

     // 里面依赖很多其它服务，没有改完全
    @GetMapping(path = "/foods/{date}/{startStation}/{endStation}/{tripId}")
    public HttpEntity getAllFood(@PathVariable String date, @PathVariable String startStation,
                                 @PathVariable String endStation, @PathVariable String tripId,
                                 @RequestHeader HttpHeaders headers) {
        System.out.println("[Food Service]Get the Get Food Request!");
        AllTripFood allTripFood = foodService.getAllFood(date, startStation, endStation, tripId, headers);
        return ok(allTripFood);
    }
}
