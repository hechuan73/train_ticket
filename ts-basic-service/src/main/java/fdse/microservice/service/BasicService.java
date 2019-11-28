package fdse.microservice.service;

import edu.fudan.common.util.Response;
import fdse.microservice.entity.*;
import org.springframework.http.HttpHeaders;

/**
 * @author Chenjie
 * @date 2017/6/6.
 */
public interface BasicService {
    Response queryForTravel(Travel info, HttpHeaders headers);
    Response queryForStationId(String stationName, HttpHeaders headers);
}
