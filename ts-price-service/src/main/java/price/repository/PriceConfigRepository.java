package price.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import price.entity.PriceConfig;
import java.util.List;
import java.util.UUID;

/**
 * @author fdse
 */
@Repository
public interface PriceConfigRepository extends CrudRepository<PriceConfig, String> {

    PriceConfig findById(String id);

    PriceConfig findByRouteIdAndTrainType(String routeId,String trainType);

    @Override
    List<PriceConfig> findAll();

}
