package price.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import price.entity.PriceConfig;
import price.repository.PriceConfigRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceConfigRepository priceConfigRepository;

    @Override
    public PriceConfig createNewPriceConfig(PriceConfig createAndModifyPriceConfig, HttpHeaders headers) {
        System.out.println("[Price Service][Create New Price Config]");
        PriceConfig priceConfig = null;
        // create
        if (createAndModifyPriceConfig.getId() == null || createAndModifyPriceConfig.getId().toString().length() < 10) {
            priceConfig = new PriceConfig();
            priceConfig.setId(UUID.randomUUID());
            priceConfig.setBasicPriceRate(createAndModifyPriceConfig.getBasicPriceRate());
            priceConfig.setFirstClassPriceRate(createAndModifyPriceConfig.getFirstClassPriceRate());
            priceConfig.setRouteId(createAndModifyPriceConfig.getRouteId());
            priceConfig.setTrainType(createAndModifyPriceConfig.getTrainType());
            priceConfigRepository.save(priceConfig);
        } else {
            // modify
            priceConfig = priceConfigRepository.findById(createAndModifyPriceConfig.getId());
            if (priceConfig == null) {
                priceConfig = new PriceConfig();
                priceConfig.setId(createAndModifyPriceConfig.getId());
            }
            priceConfig.setBasicPriceRate(createAndModifyPriceConfig.getBasicPriceRate());
            priceConfig.setFirstClassPriceRate(createAndModifyPriceConfig.getFirstClassPriceRate());
            priceConfig.setRouteId(createAndModifyPriceConfig.getRouteId());
            priceConfig.setTrainType(createAndModifyPriceConfig.getTrainType());
            priceConfigRepository.save(priceConfig);
        }
        return priceConfig;
    }

    @Override
    public PriceConfig findById(String id, HttpHeaders headers) {
        System.out.println("[Price Service][Find By Id] ID:" + id);
        PriceConfig priceConfig = priceConfigRepository.findById(UUID.fromString(id));
        return priceConfig;
    }

    @Override
    public PriceConfig findByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers) {
        System.out.println("[Price Service][Find By Route And Train Type] Rote:" + routeId + "Train Type:" + trainType);
        PriceConfig priceConfig = priceConfigRepository.findByRouteIdAndTrainType(routeId, trainType);
        System.out.println("[Price Service][Find By Route Id And Train Type]");
        return priceConfig;
    }


    @Override
    public List<PriceConfig> findAllPriceConfig(HttpHeaders headers) {
        List<PriceConfig> list = priceConfigRepository.findAll();
        if (list == null) {
            list = new ArrayList<>();
        }
        return list;
    }

    @Override
    public boolean deletePriceConfig(PriceConfig c, HttpHeaders headers) {
        PriceConfig priceConfig = priceConfigRepository.findById(c.getId());
        if (priceConfig == null) {
            return false;
        } else {
            PriceConfig pc = new PriceConfig();
            pc.setId(c.getId());
            pc.setRouteId(c.getRouteId());
            pc.setTrainType(c.getTrainType());
            pc.setBasicPriceRate(c.getBasicPriceRate());
            pc.setFirstClassPriceRate(c.getFirstClassPriceRate());
            priceConfigRepository.delete(pc);
            return true;
        }
    }

    @Override
    public boolean updatePriceConfig(PriceConfig c, HttpHeaders headers) {
        PriceConfig priceConfig = priceConfigRepository.findById(c.getId());
        if (priceConfig == null) {
            return false;
        } else {
            priceConfig.setId(c.getId());
            priceConfig.setBasicPriceRate(c.getBasicPriceRate());
            priceConfig.setFirstClassPriceRate(c.getFirstClassPriceRate());
            priceConfig.setRouteId(c.getRouteId());
            priceConfig.setTrainType(c.getTrainType());
            priceConfigRepository.save(priceConfig);
            return true;
        }
    }
}
