package consign.service;

import consign.entity.Consign;
import org.springframework.http.HttpHeaders;

import edu.fudan.common.util.Response;

import java.util.UUID;

public interface ConsignService {
    Response insertConsignRecord(Consign consignRequest, HttpHeaders headers);

    Response updateConsignRecord(Consign consignRequest, HttpHeaders headers);

    Response queryByAccountId(UUID accountId, HttpHeaders headers);

    Response queryByOrderId(UUID orderId, HttpHeaders headers);

    Response queryByConsignee(String consignee, HttpHeaders headers);
}
