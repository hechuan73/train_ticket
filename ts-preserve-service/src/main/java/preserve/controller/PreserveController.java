package preserve.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import preserve.entity.*;
import preserve.service.PreserveService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/preserveservice")
public class PreserveController {

    @Autowired
    private PreserveService preserveService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Preserve Service ] !";
    }

    @CrossOrigin(origins = "*")
    @PostMapping(value = "/preserve")
    public HttpEntity preserve(@RequestBody OrderTicketsInfo oti,
                               @RequestHeader HttpHeaders headers) {
        System.out.println("[Preserve Service][Preserve] Account " + " order from " +
                oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());
        return ok(preserveService.preserve(oti, headers));
    }

}
