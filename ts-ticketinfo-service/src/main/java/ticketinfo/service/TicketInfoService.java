package ticketinfo.service;

import ticketinfo.domain.QueryForStationId;
import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public interface TicketInfoService {
    ResultForTravel queryForTravel(QueryForTravel info);
    String queryForStationId(QueryForStationId info);
}
