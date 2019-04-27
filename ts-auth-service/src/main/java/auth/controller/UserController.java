package auth.controller;


import auth.dto.BasicAuthDto;
import auth.dto.TokenDto;
import auth.entity.User;
import auth.service.TokenService;
import auth.service.UserService;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private TokenService tokenService;

    @GetMapping("/hello")
    public Object getHello() {
        return "Hello";
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> getToken(@RequestBody BasicAuthDto dao) {
        return ResponseEntity.ok(tokenService.getToken(dao));
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Response> deleteUserById(@PathVariable String userId) {
        return ResponseEntity.ok(userService.deleteByUserId(UUID.fromString(userId)));
    }
}
