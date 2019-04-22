package auth.controller;

import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;
import auth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> getToken(@RequestBody BasicAuthDto dao) {
        return ResponseEntity.ok(tokenService.getToken(dao));
    }
}

