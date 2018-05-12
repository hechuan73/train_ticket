package consignprice.service;

import consignprice.domain.GetPriceDomain;
import consignprice.domain.PriceConfig;
import consignprice.repository.ConsignPriceConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsignPriceServiceImpl implements ConsignPriceService {
    @Autowired
    private ConsignPriceConfigRepository repository;

    @Override
    public double getPriceByWeightAndRegion(GetPriceDomain domain) {
        PriceConfig priceConfig = repository.findByIndex(0);
        double initialPrice = priceConfig.getInitialPrice();
        if(domain.getWeight() <= priceConfig.getInitialWeight()){
            return initialPrice;
        }
        else{
            double extraWeight = domain.getWeight() - priceConfig.getInitialWeight();
            if(domain.isWithinRegion())
                return initialPrice + extraWeight * priceConfig.getWithinPrice();
            else
                return initialPrice + extraWeight * priceConfig.getBeyondPrice();
        }
    }

    @Override
    public String queryPriceInformation() {
        StringBuilder sb = new StringBuilder();
        PriceConfig price = repository.findByIndex(0);
        sb.append("The price of weight within ");
        sb.append(price.getInitialWeight());
        sb.append(" is ");
        sb.append(price.getInitialPrice());
        sb.append(". The price of extra weight within the region is ");
        sb.append(price.getWithinPrice());
        sb.append(" and beyond the region is ");
        sb.append(price.getBeyondPrice());
        sb.append("\n");
        return sb.toString();
    }

    @Override
    public boolean createAndModifyPrice(PriceConfig config) {
        System.out.println("[Consign Price Service][Create New Price Config]");

        PriceConfig originalConfig;
        if(repository.findByIndex(0) != null)
            originalConfig = repository.findByIndex(0);
        else
            originalConfig = new PriceConfig();
        originalConfig.setId(config.getId());
        originalConfig.setIndex(0);
        originalConfig.setInitialPrice(config.getInitialPrice());
        originalConfig.setInitialWeight(config.getInitialWeight());
        originalConfig.setWithinPrice(config.getWithinPrice());
        originalConfig.setBeyondPrice(config.getBeyondPrice());
        repository.save(originalConfig);
        return true;
    }

    @Override
    public PriceConfig getPriceConfig() {
        return repository.findByIndex(0);
    }
}
