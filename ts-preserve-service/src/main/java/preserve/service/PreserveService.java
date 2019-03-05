package preserve.service;

import org.springframework.http.HttpHeaders;
import preserve.entity.OrderTicketsInfo;
import preserve.entity.OrderTicketsResult;

public interface PreserveService {

    OrderTicketsResult preserve(OrderTicketsInfo oti,String accountId,String loginToken,HttpHeaders headers);

}
