package route.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.models.auth.In;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

import java.util.List;

/**
 * @author fdse
 */
@Data
@AllArgsConstructor
@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@GenericGenerator(name = "jpa-uuid",strategy="uuid")
public class Route {

    @Id
    @GeneratedValue(generator = "jpa-uuid")
    @Column(length = 36)
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
