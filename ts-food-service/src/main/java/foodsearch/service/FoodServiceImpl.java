package foodsearch.service;

import foodsearch.entity.*;
import foodsearch.repository.FoodOrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FoodServiceImpl implements FoodService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private FoodOrderRepository foodOrderRepository;

    @Override
    public FoodOrder createFoodOrder(FoodOrder addFoodOrder, HttpHeaders headers) {

        FoodOrder fo = foodOrderRepository.findByOrderId(addFoodOrder.getOrderId());
        if (fo != null) {
            System.out.println("[Food-Service][AddFoodOrder] Order Id Has Existed.");
        } else {
            fo = new FoodOrder();
            fo.setId(UUID.randomUUID());
            fo.setOrderId(addFoodOrder.getOrderId());
            fo.setFoodType(addFoodOrder.getFoodType());
            if (addFoodOrder.getFoodType() == 2) {
                fo.setStationName(addFoodOrder.getStationName());
                fo.setStoreName(addFoodOrder.getStoreName());
            }
            fo.setFoodName(addFoodOrder.getFoodName());
            fo.setPrice(addFoodOrder.getPrice());
            foodOrderRepository.save(fo);
            System.out.println("[Food-Service][AddFoodOrder] Success.");
        }
        return fo;
    }

    @Override
    public FoodOrder deleteFoodOrder(String orderId, HttpHeaders headers) {
        FoodOrder foodOrder = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
        if (foodOrder == null) {
            System.out.println("[Food-Service][Cancel FoodOrder] Order Id Is Non-Existent.");
        } else {
            foodOrderRepository.deleteFoodOrderByOrderId(UUID.fromString(orderId));
            System.out.println("[Food-Service][Cancel FoodOrder] Success.");
        }
        return foodOrder;
    }

    @Override
    public List<FoodOrder> findAllFoodOrder(HttpHeaders headers) {
        return foodOrderRepository.findAll();
    }


    @Override
    public FoodOrder updateFoodOrder(FoodOrder updateFoodOrder, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findById(updateFoodOrder.getId());
        if (fo == null) {
            System.out.println("[Food-Service][Update FoodOrder] Order Id Is Non-Existent.");
        } else {
            // fo.setOrderId(UUID.fromString(ufoi.getOrderId()));
            fo.setFoodType(updateFoodOrder.getFoodType());
            if (updateFoodOrder.getFoodType() == 1) {
                fo.setStationName(updateFoodOrder.getStationName());
                fo.setStoreName(updateFoodOrder.getStoreName());
            }
            fo.setFoodName(updateFoodOrder.getFoodName());
            fo.setPrice(updateFoodOrder.getPrice());
            foodOrderRepository.save(fo);
            System.out.println("[Food-Service][Update FoodOrder] Success.");
        }
        return fo;
    }

    @Override
    public FoodOrder findByOrderId(String orderId, HttpHeaders headers) {
        FoodOrder fo = foodOrderRepository.findByOrderId(UUID.fromString(orderId));
        if (fo != null) {
            System.out.println("[Food-Service][Find Order by id] Success.");
        } else {
            System.out.println("[Food-Service][Find Order by id] Order Id Is Non-Existent.");
        }
        return fo;
    }


    @Override
    public AllTripFood getAllFood(String date, String startStation, String endStation, String tripId, HttpHeaders headers) {
        System.out.println("data=" + date + "start=" + startStation + "end=" + endStation + "tripid=" + tripId);
        AllTripFood allTripFood = null;

        if (null == tripId || tripId.length() <= 2) {
            return allTripFood;
        }

        // need return this tow element
        List<TrainFood> trainFoodList = null;
        Map<String, List<FoodStore>> foodStoreListMap = new HashMap<String, List<FoodStore>>();

//        QueryTrainFoodInfo qti = new QueryTrainFoodInfo();
//        qti.setTripId(tripId);

        /**--------------------------------------------------------------------------------------*/
        HttpEntity requestEntityGetTrainFoodListResult = new HttpEntity(null, headers);
        ResponseEntity<List<TrainFood>> reGetTrainFoodListResult = restTemplate.exchange(
                "http://ts-food-map-service:18855/api/v1/foodmap/trainfoods/" + tripId,
                HttpMethod.GET,
                requestEntityGetTrainFoodListResult,
                new ParameterizedTypeReference<List<TrainFood>>() {
                });

        List<TrainFood> trainFoodListResult = reGetTrainFoodListResult.getBody();
//        GetTrainFoodListResult  trainFoodListResult = restTemplate.postForObject
//                                        ("http://ts-food-map-service:18855/foodmap/getTrainFoodOfTrip",
//                                                qti, GetTrainFoodListResult.class);


        if (trainFoodListResult != null) {
            trainFoodList = trainFoodListResult;
            System.out.println("[Food Service]Get Train Food List!");
        } else {
            System.out.println("[Food Service]Get the Get Food Request Failed!");
//            result.setStatus(false);
//            result.setMessage(trainFoodListResult.getMessage());
            return allTripFood;
        }
        //车次途经的车站
        /**--------------------------------------------------------------------------------------*/
        HttpEntity requestEntityGetRouteResult = new HttpEntity(null, headers);

//        private boolean status;
//
//        private String message;
//
//        private Route route;

//        ResponseEntity<GetRouteResult> reGetRouteResult = restTemplate.exchange(
//                "http://ts-travel-service:12346/travel/getRouteByTripId/" + tripId,
//                HttpMethod.GET,
//                requestEntityGetRouteResult,
//                GetRouteResult.class);
//        GetRouteResult stationResult = reGetRouteResult.getBody();
////        GetRouteResult  stationResult= restTemplate.getForObject
////                                        ("http://ts-travel-service:12346/travel/getRouteByTripId/"+tripId,
////                                                GetRouteResult.class);
//
//
//        if (stationResult.isStatus()) {
//            Route route = stationResult.getRoute();
//            List<String> stations = route.getStations();
//            //去除不经过的站，如果起点终点有的话
//            if (null != startStation && !"".equals(startStation)) {
//                //name 一个 name
//                QueryForId q1 = new QueryForId();
//                q1.setName(startStation);
//
//
//                /**--------------------------------------------------------------------------------------*/
//                HttpEntity requestEntityStartStationId = new HttpEntity(q1, headers);
//                ResponseEntity<String> reStartStationId = restTemplate.exchange(
//                        "http://ts-station-service:12345/station/queryForId",
//                        HttpMethod.POST,
//                        requestEntityStartStationId,
//                        String.class);
//                String startStationId = reStartStationId.getBody();
////                String startStationId = restTemplate.postForObject
////                        ("http://ts-station-service:12345/station/queryForId", q1, String.class);
//
//
//                for (int i = 0; i < stations.size(); i++) {
//                    if (stations.get(i).equals(startStationId)) {
//                        break;
//                    } else {
//                        stations.remove(i);
//                    }
//                }
//            }
//            if (null != endStation && !"".equals(endStation)) {
//                QueryForId q2 = new QueryForId();
//                q2.setName(endStation);
//
//
//                /**--------------------------------------------------------------------------------------*/
//                HttpEntity requestEntityEndStationId = new HttpEntity(q2, headers);
//                ResponseEntity<String> reEndStationId = restTemplate.exchange(
//                        "http://ts-station-service:12345/station/queryForId",
//                        HttpMethod.POST,
//                        requestEntityEndStationId,
//                        String.class);
//                String endStationId = reEndStationId.getBody();
////                String endStationId = restTemplate.postForObject
////                        ("http://ts-station-service:12345/station/queryForId", q2, String.class);
//
//
//                for (int i = stations.size() - 1; i >= 0; i--) {
//                    if (stations.get(i).equals(endStationId)) {
//                        break;
//                    } else {
//                        stations.remove(i);
//                    }
//                }
//            }
//
//            HttpEntity requestEntityFoodStoresListResult = new HttpEntity(stations, headers);
//            ResponseEntity<List<FoodStore>> reFoodStoresListResult = restTemplate.exchange(
//                    "http://ts-food-map-service:18855/api/v1/foodmap/foodstores",
//                    HttpMethod.POST,
//                    requestEntityFoodStoresListResult,
//                    new ParameterizedTypeReference<List<FoodStore>>() {
//                    });
//
//            List<FoodStore> foodStoresListResult = reFoodStoresListResult.getBody();
//            if (foodStoresListResult != null && foodStoresListResult.size() > 0) {
//                for (String stationId : stations) {
//                    List<FoodStore> res = foodStoresListResult.stream()
//                            .filter(foodStore -> (foodStore.getStationId().equals(stationId)))
//                            .collect(Collectors.toList());
//                    foodStoreListMap.put(stationId, res);
//                }
//            } else {
//                return allTripFood;
//            }
//        } else {
//            return allTripFood;
//        }
//        allTripFood.setTrainFoodList(trainFoodList);
//        allTripFood.setFoodStoreListMap(foodStoreListMap);

        return allTripFood;
    }


}
