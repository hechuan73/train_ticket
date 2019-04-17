package consignprice.service;

import consignprice.entity.ConsignPrice;
import consignprice.repository.ConsignPriceConfigRepository;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

@Service
public class ConsignPriceServiceImpl implements ConsignPriceService {

    @Autowired
    private ConsignPriceConfigRepository repository;

    //计价
    @Override
    public Response getPriceByWeightAndRegion(double weight, boolean isWithinRegion, HttpHeaders headers) {
        ConsignPrice priceConfig = repository.findByIndex(0);
        double price = 0;
        double initialPrice = priceConfig.getInitialPrice();
        if (weight <= priceConfig.getInitialWeight()) {
            price = initialPrice;
        } else {
            double extraWeight = weight - priceConfig.getInitialWeight();
            if (isWithinRegion)
                price = initialPrice + extraWeight * priceConfig.getWithinPrice();
            else
                price = initialPrice + extraWeight * priceConfig.getBeyondPrice();
        }
        return new Response<>(1, "Success", price);
    }

    //查询价格信息
    @Override
    public Response queryPriceInformation(HttpHeaders headers) {
        StringBuilder sb = new StringBuilder();
        ConsignPrice price = repository.findByIndex(0);
        sb.append("The price of weight within ");
        sb.append(price.getInitialWeight());
        sb.append(" is ");
        sb.append(price.getInitialPrice());
        sb.append(". The price of extra weight within the region is ");
        sb.append(price.getWithinPrice());
        sb.append(" and beyond the region is ");
        sb.append(price.getBeyondPrice());
        sb.append("\n");
        return new Response<>(1, "Success", sb.toString());
    }

    //创建价格
    @Override
    public Response createAndModifyPrice(ConsignPrice config, HttpHeaders headers) {
        System.out.println("[Consign Price Service][Create New Price Config]");
        //更新price
        ConsignPrice originalConfig;
        if (repository.findByIndex(0) != null)
            originalConfig = repository.findByIndex(0);
        else
            originalConfig = new ConsignPrice();
        originalConfig.setId(config.getId());
        originalConfig.setIndex(0);
        originalConfig.setInitialPrice(config.getInitialPrice());
        originalConfig.setInitialWeight(config.getInitialWeight());
        originalConfig.setWithinPrice(config.getWithinPrice());
        originalConfig.setBeyondPrice(config.getBeyondPrice());
        repository.save(originalConfig);
        return new Response<>(1, "Success", originalConfig);
    }

    @Override
    public Response getPriceConfig(HttpHeaders headers) {
        return new Response<>(1, "Success", repository.findByIndex(0));
    }
}
