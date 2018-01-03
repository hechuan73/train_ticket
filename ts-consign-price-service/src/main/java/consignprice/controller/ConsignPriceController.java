package consignprice.controller;

import consignprice.domain.GetPriceDomain;
import consignprice.domain.PriceConfig;
import consignprice.service.ConsignPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConsignPriceController {

    @Autowired
    ConsignPriceService service;

    @RequestMapping(value = "/consignPrice/getPrice", method= RequestMethod.POST)
    public double getPriceByWeightAndRegion(@RequestBody GetPriceDomain info){
        return service.getPriceByWeightAndRegion(info);
    }

    @RequestMapping(value = "/consignPrice/getPriceInfo", method= RequestMethod.GET)
    public String getPriceInfo(){
        return service.queryPriceInformation();
    }

    @RequestMapping(value = "/consignPrice/getPriceConfig", method= RequestMethod.GET)
    public PriceConfig getPriceConfig(){
        return service.getPriceConfig();
    }

    @RequestMapping(value = "/consignPrice/modifyPriceConfig", method= RequestMethod.POST)
    public boolean modifyPriceConfig(@RequestBody PriceConfig priceConfig){
        return service.createAndModifyPrice(priceConfig);
    }
}
