package food.service;

import food.domain.*;

public interface FoodMapService {

    FoodStore createFoodStore(FoodStore fs);

    TrainFood createTrainFood(TrainFood tf);

    GetFoodStoresListResult listFoodStores();

    GetTrainFoodListResult listTrainFood();

    GetFoodStoresListResult listFoodStoresByStationId(String stationId);

    GetTrainFoodListResult listTrainFoodByTripId(String tripId);



}
