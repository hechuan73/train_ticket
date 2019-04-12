package food.service;

import food.entity.FoodStore;
import food.entity.TrainFood;
import food.repository.FoodStoreRepository;
import food.repository.TrainFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodMapServiceImpl implements FoodMapService {

    @Autowired
    FoodStoreRepository foodStoreRepository;

    @Autowired
    TrainFoodRepository trainFoodRepository;

    @Override
    public FoodStore createFoodStore(FoodStore fs, HttpHeaders headers) {
        FoodStore fsTemp = foodStoreRepository.findById(fs.getId());
        if (fsTemp != null) {
            System.out.println("[Food Map Service][Init FoodStore] Already Exists Id:" + fs.getId());
        } else {
            foodStoreRepository.save(fs);
        }
        return fs;
    }

    @Override
    public TrainFood createTrainFood(TrainFood tf, HttpHeaders headers) {
        TrainFood tfTemp = trainFoodRepository.findById(tf.getId());
        if (tfTemp != null) {
            System.out.println("[Food Map Service][Init TrainFood] Already Exists Id:" + tf.getId());
        } else {
            trainFoodRepository.save(tf);
        }
        return tf;
    }

    @Override
    public List<FoodStore> listFoodStores(HttpHeaders headers) {
        List<FoodStore> foodStores = foodStoreRepository.findAll();
        return foodStores;
    }

    @Override
    public List<TrainFood> listTrainFood(HttpHeaders headers) {
        List<TrainFood> trainFoodList = trainFoodRepository.findAll();
        return trainFoodList;
    }

    @Override
    public List<FoodStore> listFoodStoresByStationId(String stationId, HttpHeaders headers) {
        List<FoodStore> foodStoreList = foodStoreRepository.findByStationId(stationId);
        return foodStoreList;
    }

    @Override
    public List<TrainFood> listTrainFoodByTripId(String tripId, HttpHeaders headers) {
        List<TrainFood> trainFoodList = trainFoodRepository.findByTripId(tripId);
        return trainFoodList;
    }

    @Override
    public  List<FoodStore> getFoodStoresByStationIds(List<String> stationIds) {
        List<FoodStore> foodStoreList = foodStoreRepository.findByStationIdIn(stationIds);
        return foodStoreList;
    }
}
