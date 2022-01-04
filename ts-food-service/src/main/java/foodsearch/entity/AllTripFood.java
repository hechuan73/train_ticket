package foodsearch.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class AllTripFood {

    private List<TrainFood> trainFoodList;

    private Map<String, List<StationFoodStore>>  foodStoreListMap;

    public AllTripFood(){
        //Default Constructor
    }

}
