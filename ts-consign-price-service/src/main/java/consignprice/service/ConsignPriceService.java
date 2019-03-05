package consignprice.service;

import consignprice.entity.GetPriceDomain;
import consignprice.entity.PriceConfig;
import org.springframework.http.HttpHeaders;

public interface ConsignPriceService {
    double getPriceByWeightAndRegion(GetPriceDomain domain, HttpHeaders headers);
    String queryPriceInformation(HttpHeaders headers);
    boolean createAndModifyPrice(PriceConfig config, HttpHeaders headers);
    PriceConfig getPriceConfig(HttpHeaders headers);
}
