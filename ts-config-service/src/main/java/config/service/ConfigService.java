package config.service;

import config.entity.Config;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

import java.util.List;

public interface ConfigService {
    Response create(Config info, HttpHeaders headers);

    Response update(Config info, HttpHeaders headers);
    //    Config retrieve(String name, HttpHeaders headers);
    Response query(String name, HttpHeaders headers);

    Response delete(String name, HttpHeaders headers);

    Response queryAll(HttpHeaders headers);
}
