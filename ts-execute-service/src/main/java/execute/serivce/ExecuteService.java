package execute.serivce;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface ExecuteService {

    Response ticketExecute(String orderId, HttpHeaders headers);

    Response ticketCollect(String orderId, HttpHeaders headers);

}
