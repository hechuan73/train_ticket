package config.service;

import config.domain.Config;
import config.domain.Information;
import config.domain.Information2;

import java.util.List;

public interface ConfigService {
    String create(Information info);
    String update(Information info);
    Config retrieve(Information2 info);
    String query(Information2 info);
    String delete(Information2 info);
    List<Config> queryAll();
}
