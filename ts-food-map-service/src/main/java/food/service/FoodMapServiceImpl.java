package food.service;

import food.domain.FoodStore;
import food.domain.GetFoodStoresListResult;
import food.domain.GetTrainFoodListResult;
import food.domain.TrainFood;
import food.repository.FoodStoreRepository;
import food.repository.TrainFoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodMapServiceImpl implements FoodMapService{

    @Autowired
    FoodStoreRepository foodStoreRepository;
    @Autowired
    TrainFoodRepository trainFoodRepository;


    @Override
    public FoodStore createFoodStore(FoodStore fs) {
        FoodStore fsTemp = foodStoreRepository.findById(fs.getId());
        if(fsTemp != null){
            System.out.println("[Food Map Service][Init FoodStore] Already Exists Id:" + fs.getId());
        } else{
            foodStoreRepository.save(fs);
        }
        return fs;
    }

    @Override
    public TrainFood createTrainFood(TrainFood tf) {
        TrainFood tfTemp = trainFoodRepository.findById(tf.getId());
        if(tfTemp != null){
            System.out.println("[Food Map Service][Init TrainFood] Already Exists Id:" + tf.getId());
        } else {
            trainFoodRepository.save(tf);
        }
        return tf;
    }

    @Override
    public GetFoodStoresListResult listFoodStores() {
        List<FoodStore> fsList= foodStoreRepository.findAll();
        GetFoodStoresListResult result = new GetFoodStoresListResult();
        result.setStatus(true);
        result.setMessage("Success");
        result.setFoodStoreList(fsList);
        return result;
    }

    @Override
    public GetTrainFoodListResult listTrainFood() {
        List<TrainFood> tfList= trainFoodRepository.findAll();
        GetTrainFoodListResult result = new GetTrainFoodListResult();
        result.setStatus(true);
        result.setMessage("Success");
        result.setTrainFoodList(tfList);
        return result;
    }

    @Override
    public GetFoodStoresListResult listFoodStoresByStationId(String stationId) {
        List<FoodStore> fsList= foodStoreRepository.findByStationId(stationId);
        GetFoodStoresListResult result = new GetFoodStoresListResult();
        result.setStatus(true);
        result.setMessage("Success");
        result.setFoodStoreList(fsList);

        return result;
    }

    @Override
    public GetTrainFoodListResult listTrainFoodByTripId(String tripId) {
        List<TrainFood> tfList= trainFoodRepository.findByTripId(tripId);
        GetTrainFoodListResult result = new GetTrainFoodListResult();
        result.setStatus(true);
        result.setMessage("Success");
        result.setTrainFoodList(tfList);

        return result;
    }
}
