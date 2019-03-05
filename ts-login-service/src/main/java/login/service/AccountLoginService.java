package login.service;

import login.entity.LoginInfo;
import login.entity.LoginResult;
import login.entity.LogoutInfo;
import login.entity.LogoutResult;
import org.springframework.http.HttpHeaders;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AccountLoginService {

    LoginResult login(LoginInfo li, String YsbCaptcha, HttpServletResponse response, HttpHeaders headers);

    LogoutResult logout(LogoutInfo li, HttpServletRequest request, HttpServletResponse response, HttpHeaders headers);

}
