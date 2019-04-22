package auth.service;

import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;

public interface TokenService {
    TokenDto getToken(BasicAuthDto dto);
}
