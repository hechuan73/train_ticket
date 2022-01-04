package food.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@Document(collection = "stores")
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationFoodStore {

    @Id
    private UUID id;

    @NotNull
    private String stationId;

    private String storeName;

    private String telephone;

    private String businessTime;

    private double deliveryFee;

    private List<Food> foodList;

    public StationFoodStore(){
        //Default Constructor
        this.stationId = "";
    }

}
