package price.controller;

import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import price.entity.PriceConfig;
import price.service.PriceService;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/priceservice")
public class PriceController {

    @Autowired
    PriceService service;

    @GetMapping(path = "/prices/welcome")
    public String home() {
        return "Welcome to [ Price Service ] !";
    }

    @GetMapping(value = "/prices/{routeId}/{trainType}")
    public HttpEntity query(@PathVariable String routeId, @PathVariable String trainType,
                            @RequestHeader HttpHeaders headers) {
        PriceConfig priceConfig = service.findByRouteIdAndTrainType(routeId, trainType, headers);
        if (priceConfig == null) {
            return ok(new Response(0, "No that config", routeId + trainType));
        } else {
            return ok(new Response(1, "Success", priceConfig));
        }
    }

    @GetMapping(value = "/prices")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        List<PriceConfig> priceConfigs = service.findAllPriceConfig(headers);
        if (priceConfigs != null && priceConfigs.size() > 0) {
            return ok(new Response(1, "Success", priceConfigs));
        } else {
            return ok(new Response(0, "No price config", null));
        }
    }

    @PostMapping(value = "/prices")
    public HttpEntity<?> create(@RequestBody PriceConfig info,
                                @RequestHeader HttpHeaders headers) {
        PriceConfig priceConfig = service.createNewPriceConfig(info, headers);
        return new ResponseEntity<>(new Response(1, "Create success", priceConfig), HttpStatus.CREATED);
    }

    @DeleteMapping(value = "/prices")
    public HttpEntity delete(@RequestBody PriceConfig info, @RequestHeader HttpHeaders headers) {
        boolean deleteResult = service.deletePriceConfig(info, headers);
        if(deleteResult){
            return ok(new Response(1, "Delete success", info));
        }else{
            return ok(new Response(0, "No that config", info));
        }
    }

    @PutMapping(value = "/prices")
    public HttpEntity update(@RequestBody PriceConfig info, @RequestHeader HttpHeaders headers) {
        boolean updateResult = service.updatePriceConfig(info, headers);
        if(updateResult){
            return ok(new Response(1, "Update success", info));
        }else{
            return ok(new Response(0, "No that config", info));
        }

    }
}
