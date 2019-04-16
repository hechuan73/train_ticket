package rebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import rebook.entity.RebookInfo;
import rebook.service.RebookService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/rebookservice")
public class RebookController {

    @Autowired
    RebookService service;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Rebook Service ] !";
    }

    @PostMapping(value = "/rebook/difference")
    public HttpEntity payDifference(@RequestBody RebookInfo info,
                                    @RequestHeader HttpHeaders headers) {
        return ok(service.payDifference(info, headers));
    }

    @PostMapping(value = "/rebook")
    public HttpEntity rebook(@RequestBody RebookInfo info, @RequestHeader HttpHeaders headers) {
        System.out.println("[Rebook Service] OrderId:" + info.getOrderId() + "Old Trip Id:" + info.getOldTripId() + " New Trip Id:" + info.getTripId() + " Date:" + info.getDate() + " Seat Type:" + info.getSeatType());
        return ok(service.rebook(info, headers));
    }
}
