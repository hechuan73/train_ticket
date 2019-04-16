package security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import security.entity.*;
import security.service.SecurityService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/securityservice")
public class SecurityController {

    @Autowired
    private SecurityService securityService;

    @GetMapping(value = "/welcome")
    public String home(@RequestHeader HttpHeaders headers) {
        return "welcome to [Security Service]";
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/securityConfigs")
    public HttpEntity findAllSecurityConfig(@RequestHeader HttpHeaders headers) {
        System.out.println("[Security Service][Find All]");
        return ok(securityService.findAllSecurityConfig(headers));
    }

    @CrossOrigin(origins = "*")
    @PostMapping(path = "/securityConfigs")
    public HttpEntity create(@RequestBody SecurityConfig info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Security Service][Create] Name:" + info.getName());
        return ok(securityService.addNewSecurityConfig(info, headers));
    }

    @CrossOrigin(origins = "*")
    @PutMapping(path = "/securityConfigs")
    public HttpEntity update(@RequestBody SecurityConfig info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Security Service][Update] Name:" + info.getName());
        return ok(securityService.modifySecurityConfig(info, headers));
    }

    @CrossOrigin(origins = "*")
    @DeleteMapping(path = "/securityConfigs/{id}")
    public HttpEntity delete(@PathVariable String id, @RequestHeader HttpHeaders headers) {
        System.out.println("[Security Service][Delete] Id:" + id);
        return ok(securityService.deleteSecurityConfig(id, headers));
    }

    @CrossOrigin(origins = "*")
    @GetMapping(path = "/securityConfigs/{accountId}")
    public HttpEntity check(@PathVariable String accountId, @RequestHeader HttpHeaders headers) {
        System.out.println("[Security Service][Check Security] Check Account Id:" + accountId);
        return ok(securityService.check(accountId, headers));
    }
}
