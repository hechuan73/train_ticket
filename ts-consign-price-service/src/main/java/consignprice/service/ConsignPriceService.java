package consignprice.service;

import consignprice.entity.ConsignPrice;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface ConsignPriceService {

    Response getPriceByWeightAndRegion(double weight, boolean isWithinRegion, HttpHeaders headers);

    Response queryPriceInformation(HttpHeaders headers);

    Response createAndModifyPrice(ConsignPrice config, HttpHeaders headers);

    Response getPriceConfig(HttpHeaders headers);
}
