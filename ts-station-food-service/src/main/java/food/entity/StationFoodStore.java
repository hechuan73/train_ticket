package food.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Proxy;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid",strategy="uuid")
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class StationFoodStore {

    @Id
    @Column(name = "store_id")
    private String id;

    @NotNull
    private String stationId;

    private String storeName;

    private String telephone;

    private String businessTime;

    private double deliveryFee;

    @ElementCollection(targetClass = Food.class, fetch = FetchType.EAGER)
    @CollectionTable(joinColumns = @JoinColumn(name = "store_id"))
    private List<Food> foodList;

    public StationFoodStore(){
        //Default Constructor
        this.stationId = "";
    }

}
