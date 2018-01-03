package price.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import price.domain.CreateAndModifyPriceConfig;
import price.domain.QueryPriceConfigByTrainAndRoute;
import price.domain.ReturnManyPriceConfigResult;
import price.domain.ReturnSinglePriceConfigResult;
import price.service.PriceService;

@RestController
public class PriceController {

    @Autowired
    PriceService service;

    @RequestMapping(value="/price/query", method= RequestMethod.POST)
    public ReturnSinglePriceConfigResult query(@RequestBody QueryPriceConfigByTrainAndRoute info){
        return service.findByRouteIdAndTrainType(info.getRouteId(),info.getTrainType());
    }

    @RequestMapping(value="/price/queryAll", method= RequestMethod.GET)
    public ReturnManyPriceConfigResult queryAll(){
        return service.findAllPriceConfig();
    }

    @RequestMapping(value="/price/create", method= RequestMethod.POST)
    public ReturnSinglePriceConfigResult create(@RequestBody CreateAndModifyPriceConfig info){
        return service.createNewPriceConfig(info);
    }

    @RequestMapping(value="/price/delete", method= RequestMethod.POST)
    public boolean delete(@RequestBody CreateAndModifyPriceConfig info){
        return service.deletePriceConfig(info);
    }

    @RequestMapping(value="/price/update", method= RequestMethod.POST)
    public boolean update(@RequestBody CreateAndModifyPriceConfig info){
        return service.updatePriceConfig(info);
    }


}
