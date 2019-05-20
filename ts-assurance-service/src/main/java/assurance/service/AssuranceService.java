package assurance.service;

import assurance.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.List;
import java.util.UUID;

public interface AssuranceService {

    Response findAssuranceById(UUID id, HttpHeaders headers);

    Response findAssuranceByOrderId(UUID orderId, HttpHeaders headers);

    Response create(int typeIndex,String orderId , HttpHeaders headers);

    Response deleteById(UUID assuranceId, HttpHeaders headers);

    Response deleteByOrderId(UUID orderId, HttpHeaders headers);

    Response modify(String assuranceId, String orderId, int typeIndex , HttpHeaders headers);

    Response getAllAssurances(HttpHeaders headers);

    Response getAllAssuranceTypes(HttpHeaders headers);
}
