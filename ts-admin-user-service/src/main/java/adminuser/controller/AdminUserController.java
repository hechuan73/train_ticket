package adminuser.controller;

import adminuser.entity.*;
import adminuser.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/adminuserservice")
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminUser Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/adminuser/{id}")
    public HttpEntity getAllUsers(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.getAllUsers(id, headers));
    }

    @PostMapping(value = "/adminuser")
    public HttpEntity addUser(@RequestBody Account request, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.addUser(request, headers));
    }

    @PutMapping(value = "/adminuser")
    public HttpEntity updateOrder(@RequestBody Account request, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.updateUser(request, headers));
    }

    @DeleteMapping(value = "/adminuser/{loginId}/{accountId}")
    public HttpEntity deleteOrder(@PathVariable String loginId, @PathVariable String accountId, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.deleteUser(loginId, accountId, headers));
    }
}
