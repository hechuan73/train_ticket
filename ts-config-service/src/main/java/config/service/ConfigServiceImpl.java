package config.service;

import config.entity.Config;
import config.repository.ConfigRepository;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository repository;

    public Response create(Config info, HttpHeaders headers) {
        if (repository.findByName(info.getName()) != null) {
            String result = "Config " + info.getName() + " already exists.";
            return new Response<>(0, "Already exists.", result);
        } else {
            Config config = new Config(info.getName(), info.getValue(), info.getDescription());
            repository.save(config);
            return new Response<>(1, "Create success", config);
        }
    }

    public Response update(Config info, HttpHeaders headers) {
        if (repository.findByName(info.getName()) == null) {
            String result = "Config " + info.getName() + " doesn't exist.";
            return new Response<>(0, "Doesn't exist.", result);
        } else {
            Config config = new Config(info.getName(), info.getValue(), info.getDescription());
            repository.save(config);
            return new Response<>(1, "Update success", config);
        }
    }

//    public Config retrieve(String name, HttpHeaders headers){
//        Config config = repository.findByName(name);
//        if( config == null){
//            return null;
//        }else{
//            return config;
//        }
//    }

    public Response query(String name, HttpHeaders headers) {
        Config config = repository.findByName(name);
        if (config == null) {
            return new Response<>(0, "No content", null);
        } else {
            return new Response<>(1, "Success", config);
        }
    }

    public Response delete(String name, HttpHeaders headers) {
        Config config = repository.findByName(name);
        if (config == null) {
            String result = "Config " + name + " doesn't exist.";
            return new Response<>(0, "Doesn't exist.", result);
        } else {
            repository.deleteByName(name);
            return new Response<>(1, "Delete success", config);
        }
    }

    @Override
    public Response queryAll(HttpHeaders headers) {
        List<Config> configList = repository.findAll();

        if (configList != null && configList.size() > 0) {
            return new Response<>(1, "Find all  config success", configList);
        } else {
            return new Response<>(0, "No content", null);
        }
    }
}
