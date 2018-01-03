package preserve.service;

import preserve.domain.OrderTicketsInfo;
import preserve.domain.OrderTicketsResult;

public interface PreserveService {

    OrderTicketsResult preserve(OrderTicketsInfo oti,String accountId,String loginToken);

}
