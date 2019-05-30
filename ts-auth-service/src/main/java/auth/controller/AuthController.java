package auth.controller;

import auth.dto.AuthDto;
import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;
import auth.service.TokenService;
import auth.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@RequestMapping("/api/v1")
public class AuthController {

    @Autowired
    private UserService userService;

    /**
     * only while  user register, this method will be called by ts-user-service
     * to create a default role use
     * @return
     */
    @GetMapping("/hello")
    public String getHello(){
        return "hello";
    }
    @PostMapping("/auth")
    public ResponseEntity<Void> createDefaultUser(@RequestBody AuthDto dto, HttpHeaders headers) {
        log.info(dto.getUserName() +" USER NAME");
        userService.createDefaultAuthUser(dto , headers);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}

