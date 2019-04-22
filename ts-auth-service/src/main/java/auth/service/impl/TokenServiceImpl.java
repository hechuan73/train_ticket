package auth.service.impl;

import auth.constant.InfoConstant;
import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;
import auth.entity.User;
import auth.exception.UserOperationException;
import auth.repository.UserRepository;
import auth.security.jwt.JWTProvider;
import auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;

@Service
public class TokenServiceImpl implements TokenService {

    @Autowired
    private JWTProvider jwtProvider;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Override
    public TokenDto getToken(BasicAuthDto dto) {
        String username = dto.getUsername();
        String password = dto.getPassword();
        // verify username and password
        UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(upat);

        User user = this.userRepository.findByUsername(username)
                .orElseThrow(() -> new UserOperationException(MessageFormat.format(
                        InfoConstant.USER_NAME_NOT_FOUND_1, username
                )));
        String token = jwtProvider.createToken(user);
        return new TokenDto(username, token);
    }
}
