package travel2.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.Valid;

/**
 * Created by Chenjie Xu on 2017/5/8.
 */
@Document(collection="trainType")
public class TrainType {
    @Valid
    @Id
    private String id;      //车型ID，每个车型一个ID，比如某个型号的动车

    @Valid
    private int economyClass;   //普通座的座位数量

    @Valid
    private int confortClass;   //商务座的座位数量

    private int averageSpeed;

    public TrainType(){
        //Default Constructor
    }

    public TrainType(String id, int economyClass, int confortClass) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
    }

    public TrainType(String id, int economyClass, int confortClass, int averageSpeed) {
        this.id = id;
        this.economyClass = economyClass;
        this.confortClass = confortClass;
        this.averageSpeed = averageSpeed;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getEconomyClass() {
        return economyClass;
    }

    public void setEconomyClass(int economyClass) {
        this.economyClass = economyClass;
    }

    public int getConfortClass() {
        return confortClass;
    }

    public void setConfortClass(int confortClass) {
        this.confortClass = confortClass;
    }

    public int getAverageSpeed() {
        return averageSpeed;
    }

    public void setAverageSpeed(int averageSpeed) {
        this.averageSpeed = averageSpeed;
    }
}
