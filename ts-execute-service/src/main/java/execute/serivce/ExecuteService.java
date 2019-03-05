package execute.serivce;

import execute.entity.TicketExecuteInfo;
import execute.entity.TicketExecuteResult;
import org.springframework.http.HttpHeaders;

public interface ExecuteService {

    TicketExecuteResult ticketExecute(TicketExecuteInfo info, HttpHeaders headers);

    TicketExecuteResult ticketCollect(TicketExecuteInfo info, HttpHeaders headers);

}
