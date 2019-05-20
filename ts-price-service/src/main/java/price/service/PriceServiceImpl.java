package price.service;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import price.entity.PriceConfig;
import price.repository.PriceConfigRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class PriceServiceImpl implements PriceService {

    @Autowired
    private PriceConfigRepository priceConfigRepository;

    @Override
    public Response createNewPriceConfig(PriceConfig createAndModifyPriceConfig, HttpHeaders headers) {
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
        return new Response<>(1, "Create success", priceConfig);
    }

    @Override
    public PriceConfig findById(String id, HttpHeaders headers) {
        System.out.println("[Price Service][Find By Id] ID:" + id);
        PriceConfig priceConfig = priceConfigRepository.findById(UUID.fromString(id));
        return priceConfig;
    }

    @Override
    public Response findByRouteIdAndTrainType(String routeId, String trainType, HttpHeaders headers) {
        System.out.println("[Price Service][Find By Route And Train Type] Rote:" + routeId + "Train Type:" + trainType);
        PriceConfig priceConfig = priceConfigRepository.findByRouteIdAndTrainType(routeId, trainType);
        System.out.println("[Price Service][Find By Route Id And Train Type]");

        if (priceConfig == null) {
            return new Response<>(0, "No that config", routeId + trainType);
        } else {
            return new Response<>(1, "Success", priceConfig);
        }
    }


    @Override
    public Response findAllPriceConfig(HttpHeaders headers) {
        List<PriceConfig> list = priceConfigRepository.findAll();
        if (list == null) {
            list = new ArrayList<>();
        }

        if (list.size() > 0) {
            return new Response<>(1, "Success", list);
        } else {
            return new Response<>(0, "No price config", null);
        }

    }

    @Override
    public Response deletePriceConfig(PriceConfig c, HttpHeaders headers) {
        PriceConfig priceConfig = priceConfigRepository.findById(c.getId());
        if (priceConfig == null) {
            return new Response<>(0, "No that config", null);
        } else {
            PriceConfig pc = new PriceConfig();
            pc.setId(c.getId());
            pc.setRouteId(c.getRouteId());
            pc.setTrainType(c.getTrainType());
            pc.setBasicPriceRate(c.getBasicPriceRate());
            pc.setFirstClassPriceRate(c.getFirstClassPriceRate());
            priceConfigRepository.delete(pc);
            return new Response<>(1, "Delete success", pc);
        }
    }

    @Override
    public Response updatePriceConfig(PriceConfig c, HttpHeaders headers) {
        PriceConfig priceConfig = priceConfigRepository.findById(c.getId());
        if (priceConfig == null) {
            return new Response<>(0, "No that config", null);
        } else {
            priceConfig.setId(c.getId());
            priceConfig.setBasicPriceRate(c.getBasicPriceRate());
            priceConfig.setFirstClassPriceRate(c.getFirstClassPriceRate());
            priceConfig.setRouteId(c.getRouteId());
            priceConfig.setTrainType(c.getTrainType());
            priceConfigRepository.save(priceConfig);
            return new Response<>(1, "Update success", priceConfig);
        }
    }
}
