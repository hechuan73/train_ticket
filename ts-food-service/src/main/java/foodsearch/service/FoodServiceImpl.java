package foodsearch.service;

import foodsearch.domain.*;
import foodsearch.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class FoodServiceImpl implements FoodService{

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Override
    public GetAllFoodOfTripResult getAllFood(String date, String startStation, String endStation, String tripId) {
        System.out.println("data=" + date + "start=" + startStation + "end=" + endStation + "tripid=" + tripId);
        GetAllFoodOfTripResult result = new GetAllFoodOfTripResult();

        if(null == tripId || tripId.length() <= 2){
            result.setStatus(false);
            result.setMessage("The tripId is null or too short");
            return result;
        }

        List<TrainFood> trainFoodList = null;
        Map<String, List<FoodStore>> foodStoreListMap = new HashMap<String, List<FoodStore>>();

        QueryTrainFoodInfo qti = new QueryTrainFoodInfo();
        qti.setTripId(tripId);

        GetTrainFoodListResult  trainFoodListResult = restTemplate.postForObject
                                        ("http://ts-food-map-service:18855/foodmap/getTrainFoodOfTrip",
                                                qti, GetTrainFoodListResult.class);
        if( trainFoodListResult.isStatus()){
            trainFoodList = trainFoodListResult.getTrainFoodList();
            System.out.println("[Food Service]Get Train Food List!");
        } else {
            System.out.println("[Food Service]Get the Get Food Request Failed!");
            result.setStatus(false);
            result.setMessage(trainFoodListResult.getMessage());
            return result;
        }

        GetRouteResult  stationResult= restTemplate.getForObject
                                        ("http://ts-travel-service:12346/travel/getRouteByTripId/"+tripId,
                                                GetRouteResult.class);
        if( stationResult.isStatus() ){
            Route route = stationResult.getRoute();
            List<String> stations = route.getStations();
            if(null != startStation && !"".equals(startStation)){
                QueryForId q1=new QueryForId();
                q1.setName(startStation);
                String startStationId = restTemplate.postForObject
                        ("http://ts-station-service:12345/station/queryForId", q1, String.class);
                for(int i = 0; i < stations.size(); i++){
                    if(stations.get(i).equals(startStationId)){
                        break;
                    } else {
                        stations.remove(i);
                    }
                }
            }
            if(null != endStation && !"".equals(endStation)){
                QueryForId q2=new QueryForId();
                q2.setName(endStation);
                String endStationId = restTemplate.postForObject
                        ("http://ts-station-service:12345/station/queryForId", q2, String.class);
                for(int i = stations.size()-1; i >= 0 ; i--){
                    if(stations.get(i).equals(endStationId)){
                        break;
                    } else {
                        stations.remove(i);
                    }
                }
            }

            for(String s:stations){
                QueryFoodStoresInfo qsi = new QueryFoodStoresInfo();
                qsi.setStationId(s);
                GetFoodStoresListResult foodStoresListResult = restTemplate.postForObject
                                            ("http://ts-food-map-service:18855/foodmap/getFoodStoresOfStation",
                                                    qsi, GetFoodStoresListResult.class);
                if(foodStoresListResult.isStatus()){
                    if( null != foodStoresListResult.getFoodStoreList()){
                        System.out.println("[Food Service]Get the Food Store!");
                        foodStoreListMap.put(s, foodStoresListResult.getFoodStoreList());
                    }
                } else {
                    result.setStatus(false);
                    result.setMessage(foodStoresListResult.getMessage());
                    return result;
                }
            }
        } else {
            result.setStatus(false);
            result.setMessage(stationResult.getMessage());
            return result;
        }

        result.setStatus(true);
        result.setMessage("Successed");
        result.setTrainFoodList(trainFoodList);
        result.setFoodStoreListMap(foodStoreListMap);

        return result;
    }

    @Override
    public AddFoodOrderResult createFoodOrder(AddFoodOrderInfo afoi) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(afoi.getOrderId()));
        AddFoodOrderResult result = new AddFoodOrderResult();
        if(fo != null){
            System.out.println("[Food-Service][AddFoodOrder] Order Id Has Existed.");
            result.setStatus(false);
            result.setMessage("OrderId has existed");
            result.setFoodOrder(null);
        } else {
            fo = new FoodOrder();
            fo.setId(UUID.randomUUID());
            fo.setOrderId(UUID.fromString(afoi.getOrderId()));
            fo.setFoodType(afoi.getFoodType());
            if(afoi.getFoodType() == 2){
                fo.setStationName(afoi.getStationName());
                fo.setStoreName(afoi.getStoreName());
            }
            fo.setFoodName(afoi.getFoodName());
            fo.setPrice(afoi.getPrice());
            foodOrderRepository.save(fo);
            System.out.println("[Food-Service][AddFoodOrder] Success.");
            result.setStatus(true);
            result.setMessage("Success");
            result.setFoodOrder(fo);
        }

        return result;
    }

    @Override
    public CancelFoodOrderResult cancelFoodOrder(CancelFoodOrderInfo cfoi) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(cfoi.getOrderId()));
        CancelFoodOrderResult result = new  CancelFoodOrderResult();
        if(fo == null){
            System.out.println("[Food-Service][Cancel FoodOrder] Order Id Is Non-Existent.");
            result.setStatus(false);
            result.setMessage("Order Id Is Non-Existent.");
            result.setFoodOrder(null);
        } else {
            foodOrderRepository.deleteFoodOrderByOrderId(UUID.fromString(cfoi.getOrderId()));
            System.out.println("[Food-Service][Cancel FoodOrder] Success.");
            result.setStatus(true);
            result.setMessage("Success");
            result.setFoodOrder(fo);
        }

        return result;
    }

    @Override
    public UpdateFoodOrderResult updateFoodOrder(UpdateFoodOrderInfo ufoi) {
        FoodOrder fo = foodOrderRepository.findById(UUID.fromString(ufoi.getId()));
        UpdateFoodOrderResult result = new UpdateFoodOrderResult();
        if(fo == null){
            System.out.println("[Food-Service][Update FoodOrder] Order Id Is Non-Existent.");
            result.setStatus(false);
            result.setMessage("Order Id Is Non-Existent.");
            result.setFoodOrder(null);
        } else {
//            fo.setOrderId(UUID.fromString(ufoi.getOrderId()));
            fo.setFoodType(ufoi.getFoodType());
            if(ufoi.getFoodType() == 1){
                fo.setStationName(ufoi.getStationName());
                fo.setStoreName(ufoi.getStoreName());
            }
            fo.setFoodName(ufoi.getFoodName());
            fo.setPrice(ufoi.getPrice());
            foodOrderRepository.save(fo);
            System.out.println("[Food-Service][Update FoodOrder] Success.");
            result.setStatus(true);
            result.setMessage("Success");
            result.setFoodOrder(fo);
        }

        return result;
    }

    @Override
    public List<FoodOrder> findAllFoodOrder() {
        return foodOrderRepository.findAll();
    }

    @Override
    public FindByOrderIdResult findByOrderId(String orderId) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
       FindByOrderIdResult result = new FindByOrderIdResult();
       if(fo != null ){
           result.setStatus(true);
           result.setMessage("Success");
           result.setFoodOrder(fo);
       } else {
           result.setStatus(false);
           result.setMessage("Order Id Is Non-Existent.");
           result.setFoodOrder(null);
       }
        return result;
    }


}
