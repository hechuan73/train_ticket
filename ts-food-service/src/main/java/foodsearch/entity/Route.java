package foodsearch.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.util.List;

@Data
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    @Id
    private String id;

    @ElementCollection(targetClass = String.class)
    private List<String> stations;

    @ElementCollection(targetClass = Integer.class)
    private List<Integer> distances;

    private String startStationId;

    private String terminalStationId;

    public Route() {
        //Default Constructor
    }

}
