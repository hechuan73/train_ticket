package foodsearch.entity;

import lombok.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.persistence.ElementCollection;
import javax.persistence.Id;
import javax.persistence.Entity;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Route {

    private String id;

    private List<String> stations;

    private List<Integer> distances;

    private String startStationId;

    private String terminalStationId;

    public Route() {
        //Default Constructor
    }

}
