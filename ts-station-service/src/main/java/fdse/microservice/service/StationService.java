package fdse.microservice.service;

import fdse.microservice.domain.Information;
import fdse.microservice.domain.QueryForId;
import fdse.microservice.domain.QueryStation;
import fdse.microservice.domain.Station;

import java.util.List;

public interface StationService {
    //CRUD
    boolean create(Information info);
    boolean exist(QueryStation info);
    boolean update(Information info);
    boolean delete(Information info);
    List<Station> query();
    String queryForId(QueryForId info);
    QueryStation queryById(String stationId);
}
