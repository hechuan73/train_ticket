package fdse.microservice.service;
import org.springframework.http.HttpHeaders;
import fdse.microservice.domain.Information;
import fdse.microservice.domain.QueryForId;
import fdse.microservice.domain.QueryStation;
import fdse.microservice.domain.Station;

import java.util.List;

public interface StationService {
    //CRUD
    boolean create(Information info,HttpHeaders headers);
    boolean exist(QueryStation info,HttpHeaders headers);
    boolean update(Information info,HttpHeaders headers);
    boolean delete(Information info,HttpHeaders headers);
    List<Station> query(HttpHeaders headers);
    String queryForId(QueryForId info,HttpHeaders headers);
    QueryStation queryById(String stationId,HttpHeaders headers);
}
