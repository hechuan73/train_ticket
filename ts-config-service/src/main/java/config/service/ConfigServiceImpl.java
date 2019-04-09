package config.service;

import config.entity.Config;
import config.repository.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository repository;

    public String create(Config info, HttpHeaders headers){
        if(repository.findByName(info.getName()) != null){
            String result = "Config " + info.getName() + " already exists.";
            return result;
        }else{
            Config config = new Config(info.getName(),info.getValue(),info.getDescription());
            repository.save(config);
            return "true";
        }
    }

    public String update(Config info, HttpHeaders headers){
        if(repository.findByName(info.getName()) == null){
            String result = "Config " + info.getName() + " doesn't exist.";
            return result;
        }else{
            Config config = new Config(info.getName(),info.getValue(),info.getDescription());
            repository.save(config);
            return "true";
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

    public Config query(String name, HttpHeaders headers){
        Config config = repository.findByName(name);
        if( config == null){
            return null;
        }else{
            return config;
        }
    }

    public String delete(String name, HttpHeaders headers){
        Config config = repository.findByName(name);
        if(config == null){
            String result = "Config " + name + " doesn't exist.";
            return result;
        }else{
            repository.deleteByName(name);
            return "true";
        }
    }

    @Override
    public List<Config> queryAll(HttpHeaders headers){
        return repository.findAll();
    }
}
