package foodsearch.service;

import foodsearch.domain.*;

import java.util.List;

public interface FoodService {

    GetAllFoodOfTripResult getAllFood(String date, String startStation, String endStation, String tripId);

    AddFoodOrderResult createFoodOrder(AddFoodOrderInfo afoi);

    CancelFoodOrderResult cancelFoodOrder(CancelFoodOrderInfo cfoi);

    UpdateFoodOrderResult updateFoodOrder(UpdateFoodOrderInfo ufoi);

    List<FoodOrder> findAllFoodOrder();

    FindByOrderIdResult findByOrderId(String orderId);
}
