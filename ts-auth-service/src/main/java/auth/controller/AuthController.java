package auth.controller;

import auth.dto.AuthDto;
import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;
import auth.service.TokenService;
import auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * only while  user register, this method will be called by ts-user-service
     * to create a default role user
     *
     * @param dto uuid id , username  password
     * @return
     */
    @PostMapping("/auth")
    public ResponseEntity<Void> createDefaultUser(@RequestBody AuthDto dto) {
        userService.createDefaultAuthUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

