package food.service;

import food.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.List;
public interface FoodMapService {

    // create data
    FoodStore createFoodStore(FoodStore fs, HttpHeaders headers);
    TrainFood createTrainFood(TrainFood tf, HttpHeaders headers);

    // query all food
    List<FoodStore> listFoodStores(HttpHeaders headers);
    List<TrainFood> listTrainFood(HttpHeaders headers);

    // query according id
    List<FoodStore>  listFoodStoresByStationId(String stationId, HttpHeaders headers);
    List<TrainFood> listTrainFoodByTripId(String tripId, HttpHeaders headers);

    List<FoodStore> getFoodStoresByStationIds(List<String> stationIds);
}
