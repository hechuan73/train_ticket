package consignprice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.UUID;

/**
 * @author fdse
 */
@Data
@Document(collection = "consign_price")
public class ConsignPrice {
    @Id
    private UUID id;
    private int index;
    private double initialWeight;
    private double initialPrice;
    private double withinPrice;
    private double beyondPrice;

    public ConsignPrice(){
        //Default Constructor
    }

}
