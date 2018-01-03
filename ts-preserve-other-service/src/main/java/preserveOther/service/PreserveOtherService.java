package preserveOther.service;

import preserveOther.domain.OrderTicketsInfo;
import preserveOther.domain.OrderTicketsResult;

public interface PreserveOtherService {

    OrderTicketsResult preserve(OrderTicketsInfo oti,String accountId,String loginToken);

}
