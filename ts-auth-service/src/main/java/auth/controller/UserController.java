package auth.controller;

import auth.dto.AuthDto;
import auth.entity.User;
import auth.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public Object getHello() {
        return "Hello";
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok().body(userService.getAllUser());
    }

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
