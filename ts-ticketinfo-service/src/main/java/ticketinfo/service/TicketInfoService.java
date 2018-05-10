package ticketinfo.service;

import ticketinfo.domain.QueryForStationId;
import ticketinfo.domain.QueryForTravel;
import ticketinfo.domain.ResultForTravel;


public interface TicketInfoService {
    ResultForTravel queryForTravel(QueryForTravel info);
    String queryForStationId(QueryForStationId info);
}
