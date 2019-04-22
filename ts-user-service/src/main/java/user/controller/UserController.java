package user.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import user.dto.UserDto;
import user.entity.User;
import user.service.UserService;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/hello")
    public String testHello() {
        return "Hello";
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{userName}")
    public Object getUserByUserName(@PathVariable String userName) {
        return userService.findByUserName(userName);
    }

    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody UserDto userDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.saveUser(userDto));
    }


}
