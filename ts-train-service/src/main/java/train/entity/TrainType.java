package train.entity;

import lombok.Data;
import lombok.NonNull;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import javax.persistence.Entity;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Data
@Entity
@GenericGenerator(name = "jpa-uuid",strategy="uuid")
public class TrainType {
    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
    private String idx;

    @NotNull
    @Column(name = "train_type_id", unique = true)
    private String id;

    @Valid
    @Column(name = "economy_class")
    private int economyClass;
    @Valid
    @Column(name = "confort_class")
    private int confortClass;
    @Column(name = "average_speed")
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

}
