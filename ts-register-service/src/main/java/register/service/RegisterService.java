package register.service;

import register.domain.RegisterInfo;
import register.domain.RegisterResult;

public interface RegisterService {

    RegisterResult create(RegisterInfo ri,String YsbCaptcha);

}
