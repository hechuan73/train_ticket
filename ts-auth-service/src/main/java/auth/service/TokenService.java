package auth.service;

import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface TokenService {
    Response getToken(BasicAuthDto dto, HttpHeaders headers);


}
