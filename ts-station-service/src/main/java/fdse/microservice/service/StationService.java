package fdse.microservice.service;
import fdse.microservice.entity.*;
import org.springframework.http.HttpHeaders;

import java.util.ArrayList;
import java.util.List;

public interface StationService {
    //CRUD
    boolean create(Station info,HttpHeaders headers);
    boolean exist(String stationName,HttpHeaders headers);
    boolean update(Station info,HttpHeaders headers);
    boolean delete(Station info,HttpHeaders headers);
    List<Station> query(HttpHeaders headers);

    String queryForId(String stationName,HttpHeaders headers);
    List<String> queryForIdBatch(List<String> nameList, HttpHeaders headers);

    String  queryById(String stationId,HttpHeaders headers);
    List<String> queryByIdBatch(List<String> stationIdList, HttpHeaders headers);

}
