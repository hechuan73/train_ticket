package security.service;

import security.domain.*;

public interface SecurityService {

    GetAllSecurityConfigResult findAllSecurityConfig();

    CreateSecurityConfigResult addNewSecurityConfig(CreateSecurityConfigInfo info);

    UpdateSecurityConfigResult modifySecurityConfig(UpdateSecurityConfigInfo info);

    DeleteConfigResult deleteSecurityConfig(DeleteConfigInfo info);

    CheckResult check(CheckInfo info);

}
