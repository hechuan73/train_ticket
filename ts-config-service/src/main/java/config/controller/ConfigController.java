package config.controller;

/**
 * Created by Chenjie Xu on 2017/5/11.
 */

import config.entity.Config;
import config.service.ConfigService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("api/v1/configservice")
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
        if (configList != null && configList.size() > 0) {
            return ok(new Response(1, "Find all  config success", configList));
        } else {
            return ok(new Response(0, "No content", null));
        }
    }


    @CrossOrigin(origins = "*")
    @PostMapping(value = "/configs")
    public HttpEntity<?> createConfig(@RequestBody Config info, @RequestHeader HttpHeaders headers) {
        String createResult = configService.create(info, headers);
        if ("true".equals(createResult)) {
            return new ResponseEntity<>(new Response(1, "Create success", createResult), HttpStatus.CREATED);
        } else {
            return ok(new Response(0, "Already exists.", createResult));
        }
    }

    @CrossOrigin(origins = "*")
    @PutMapping(value = "/configs")
    public HttpEntity updateConfig(@RequestBody Config info, @RequestHeader HttpHeaders headers) {
        String updateResult = configService.update(info, headers);
        if ("true".equals(updateResult)) {
            return ok(new Response(1, "Update success", updateResult));
        } else {
            return ok(new Response(0, "Doesn't exist.", updateResult));
        }
    }


    @CrossOrigin(origins = "*")
    @DeleteMapping(value = "/configs/{configName}")
    public HttpEntity deleteConfig(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        String deleteResult = configService.delete(configName, headers);
        if ("true".equals(deleteResult)) {
            return ok(new Response(1, "Delete success", deleteResult));
        } else {
            return ok(new Response(0, "Doesn't exist.", deleteResult));
        }
    }

    @CrossOrigin(origins = "*")
    @GetMapping(value = "/configs/{configName}")
    public HttpEntity retrieve(@PathVariable String configName, @RequestHeader HttpHeaders headers) {
        Config config = configService.query(configName, headers);
        if (config == null) {
            return ok(new Response(0, "No content", null));
        } else {
            return ok(new Response(1, "Success", config));
        }
    }

//    @CrossOrigin(origins = "*")
//    @RequestMapping(value="/config/query", method = RequestMethod.POST)
//    public String query(@PathVariable String name, @RequestHeader HttpHeaders headers){
//        return configService.query(info, headers);
//    }


}
