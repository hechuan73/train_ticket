package preserveOther.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import preserveOther.entity.OrderTicketsInfo;
import preserveOther.service.PreserveOtherService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/preserveotherservice")
public class PreserveOtherController {

    @Autowired
    private PreserveOtherService preserveService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ PreserveOther Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value = "/preserveOther")
    public HttpEntity preserve(@RequestBody OrderTicketsInfo oti,
                               @RequestHeader HttpHeaders headers) {
        System.out.println("[Preserve Other Service][Preserve] Account " + " order from " +
                oti.getFrom() + " -----> " + oti.getTo() + " at " + oti.getDate());
        return ok(preserveService.preserve(oti, headers));
    }
}
