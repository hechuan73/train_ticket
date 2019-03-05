package security.service;

import org.springframework.http.HttpHeaders;
import security.entity.*;

public interface SecurityService {

    GetAllSecurityConfigResult findAllSecurityConfig(HttpHeaders headers);

    CreateSecurityConfigResult addNewSecurityConfig(CreateSecurityConfigInfo info, HttpHeaders headers);

    UpdateSecurityConfigResult modifySecurityConfig(UpdateSecurityConfigInfo info, HttpHeaders headers);

    DeleteConfigResult deleteSecurityConfig(DeleteConfigInfo info, HttpHeaders headers);

    CheckResult check(CheckInfo info,HttpHeaders headers);

}
