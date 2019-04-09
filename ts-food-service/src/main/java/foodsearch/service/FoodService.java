package foodsearch.service;

import foodsearch.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface FoodService {

    FoodOrder createFoodOrder(FoodOrder afoi, HttpHeaders headers);
    FoodOrder deleteFoodOrder(String orderId, HttpHeaders headers);

    FoodOrder findByOrderId(String orderId, HttpHeaders headers);

    FoodOrder updateFoodOrder(FoodOrder updateFoodOrder, HttpHeaders headers);

    List<FoodOrder> findAllFoodOrder(HttpHeaders headers);

    AllTripFood getAllFood(String date, String startStation, String endStation, String tripId, HttpHeaders headers);

}
