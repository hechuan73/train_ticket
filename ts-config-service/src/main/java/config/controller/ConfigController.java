package config.controller;

/**
 * Created by Chenjie Xu on 2017/5/11.
 */

import config.entity.Config;
import config.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/config")
public class ConfigController {

    @Autowired
    private ConfigService configService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ Config Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/configs")
    public HttpEntity queryAll(@RequestHeader HttpHeaders headers) {
        List<Config> configList = configService.queryAll(headers);
        return ok(configList);
    }


    @CrossOrigin(origins = "*")
    @PostMapping(value = "/configs")
    public HttpEntity<?> createConfig(@RequestBody Config info, @RequestHeader HttpHeaders headers) {
        String createResult = configService.create(info, headers);
        return new ResponseEntity<>(createResult, HttpStatus.CREATED);
    }

    @CrossOrigin(origins = "*")
    @PatchMapping(value = "/configs")
    public HttpEntity<?> updateConfig(@RequestBody Config info, @RequestHeader HttpHeaders headers) {
        String updateResult = configService.update(info, headers);
        return new ResponseEntity<>(updateResult, HttpStatus.ACCEPTED);
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/configs/{configName}")
    public HttpEntity<?> deleteConfig(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        String deleteResult = configService.delete(configName, headers);
        return new ResponseEntity<>(deleteResult, HttpStatus.ACCEPTED);
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/configs/{configName}")
    public HttpEntity retrieve(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        Config config = configService.query(configName, headers);
        return ok(config);
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(value="/config/query", method = RequestMethod.POST)
//    public String query(@PathVariable String name, @RequestHeader HttpHeaders headers){
//        return configService.query(info, headers);
//    }


}
