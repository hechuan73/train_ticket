package cancel.service;


import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface CancelService {

    Response cancelOrder(String orderId, String loginId, HttpHeaders headers) throws Exception;

    Response calculateRefund(String orderId, HttpHeaders headers);

}
