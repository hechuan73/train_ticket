package ticketinfo.service;

import org.springframework.http.HttpHeaders;
import ticketinfo.entity.QueryForStationId;
import ticketinfo.entity.QueryForTravel;
import ticketinfo.entity.ResultForTravel;

/**
 * Created by Chenjie Xu on 2017/6/6.
 */
public interface TicketInfoService {
    ResultForTravel queryForTravel(QueryForTravel info,HttpHeaders headers);
    String queryForStationId(QueryForStationId info,HttpHeaders headers);
}
