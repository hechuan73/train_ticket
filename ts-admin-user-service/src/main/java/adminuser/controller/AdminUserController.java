package adminuser.controller;

import adminuser.dto.UserDto;
import adminuser.service.AdminUserService;
import com.netflix.hystrix.contrib.javanica.annotation.DefaultProperties;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.ResponseEntity.ok;

/**
 * @author fdse
 */
@RestController
@RequestMapping("/api/v1/adminuserservice/users")
@DefaultProperties(defaultFallback = "fallback", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "15000")
})
public class AdminUserController {

    @Autowired
    AdminUserService adminUserService;

    @GetMapping(path = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "Welcome to [ AdminUser Service ] !";
    }

    @CrossOrigin(origins = "*")
    @GetMapping
    @HystrixCommand
    public HttpEntity getAllUsers(@RequestHeader HttpHeaders headers) {
        return ok(adminUserService.getAllUsers(headers));
    }

    @PutMapping
    @HystrixCommand
    public HttpEntity updateUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.updateUser(userDto, headers));
    }


    @PostMapping
    @HystrixCommand
    public HttpEntity addUser(@RequestBody UserDto userDto, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.addUser(userDto, headers));
    }

    @DeleteMapping(value = "/{userId}")
    @HystrixCommand
    public HttpEntity deleteUser(@PathVariable String userId, @RequestHeader HttpHeaders headers) {
        return ok(adminUserService.deleteUser(userId, headers));
    }


    private HttpEntity fallback() {
        return ok(new Response<>());
    }
}
