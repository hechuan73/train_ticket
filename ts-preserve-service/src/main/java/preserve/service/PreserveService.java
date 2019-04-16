package preserve.service;

import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;
import preserve.entity.OrderTicketsInfo;
import preserve.entity.OrderTicketsResult;

public interface PreserveService {

    Response preserve(OrderTicketsInfo oti, HttpHeaders headers);
}
