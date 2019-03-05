package cancel.service;

import cancel.entity.CalculateRefundResult;
import cancel.entity.CancelOrderInfo;
import cancel.entity.CancelOrderResult;
import org.springframework.http.HttpHeaders;

public interface CancelService {

    CancelOrderResult cancelOrder(CancelOrderInfo info,String loginToken,String loginId, HttpHeaders headers) throws Exception;

    CalculateRefundResult calculateRefund(CancelOrderInfo info, HttpHeaders headers);

}
