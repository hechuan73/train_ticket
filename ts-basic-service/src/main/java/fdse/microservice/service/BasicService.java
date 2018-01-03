package fdse.microservice.service;

import fdse.microservice.domain.*;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public interface BasicService {
    ResultForTravel queryForTravel(QueryForTravel info);
    String queryForStationId(QueryStation info);
}
