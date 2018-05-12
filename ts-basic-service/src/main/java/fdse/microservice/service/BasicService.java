package fdse.microservice.service;

import fdse.microservice.domain.*;


public interface BasicService {
    ResultForTravel queryForTravel(QueryForTravel info);
    String queryForStationId(QueryStation info);
}
