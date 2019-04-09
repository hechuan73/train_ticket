package config.service;

import config.entity.Config;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface ConfigService {
    String create(Config info, HttpHeaders headers);

    String update(Config info, HttpHeaders headers);
    //    Config retrieve(String name, HttpHeaders headers);
    Config query(String name, HttpHeaders headers);

    String delete(String name, HttpHeaders headers);

    List<Config> queryAll(HttpHeaders headers);
}
