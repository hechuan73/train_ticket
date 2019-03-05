package register.service;

import org.springframework.http.HttpHeaders;
import register.entity.RegisterInfo;
import register.entity.RegisterResult;

public interface RegisterService {

    RegisterResult create(RegisterInfo ri,String YsbCaptcha, HttpHeaders headers);

}
