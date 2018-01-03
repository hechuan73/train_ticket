package execute.serivce;

import execute.domain.TicketExecuteInfo;
import execute.domain.TicketExecuteResult;
import sun.security.krb5.internal.Ticket;

public interface ExecuteService {

    TicketExecuteResult ticketExecute(TicketExecuteInfo info);

    TicketExecuteResult ticketCollect(TicketExecuteInfo info);

}
