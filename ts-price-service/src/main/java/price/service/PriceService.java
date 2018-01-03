package price.service;

import price.domain.CreateAndModifyPriceConfig;
import price.domain.ReturnManyPriceConfigResult;
import price.domain.ReturnSinglePriceConfigResult;

public interface PriceService {

    ReturnSinglePriceConfigResult createNewPriceConfig(CreateAndModifyPriceConfig createAndModifyPriceConfig);

    ReturnSinglePriceConfigResult findById(String id);

    ReturnSinglePriceConfigResult findByRouteIdAndTrainType(String routeId,String trainType);

    ReturnManyPriceConfigResult findAllPriceConfig();

    boolean deletePriceConfig(CreateAndModifyPriceConfig c);

    boolean updatePriceConfig(CreateAndModifyPriceConfig c);

}
