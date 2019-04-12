package price.service;

import org.springframework.http.HttpHeaders;

import price.entity.PriceConfig;

import java.util.List;

public interface PriceService {

    PriceConfig createNewPriceConfig(PriceConfig priceConfig, HttpHeaders headers);

    PriceConfig findById(String id, HttpHeaders headers);

    PriceConfig findByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers);

    List<PriceConfig> findAllPriceConfig(HttpHeaders headers);

    boolean deletePriceConfig(PriceConfig c, HttpHeaders headers);

    boolean updatePriceConfig(PriceConfig c, HttpHeaders headers);

}
