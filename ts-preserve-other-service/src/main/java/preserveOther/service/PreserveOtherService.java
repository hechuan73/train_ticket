package preserveOther.service;

import org.springframework.http.HttpHeaders;
import preserveOther.entity.OrderTicketsInfo;
import preserveOther.entity.OrderTicketsResult;

public interface PreserveOtherService {

    OrderTicketsResult preserve(OrderTicketsInfo oti,String accountId,String loginToken, HttpHeaders headers);

}
