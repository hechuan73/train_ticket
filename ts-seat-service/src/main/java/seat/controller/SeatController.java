package seat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import seat.entity.Seat;
import seat.entity.Ticket;
import seat.service.SeatService;

import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping("/api/v1/seatservice")
public class SeatController {

    @Autowired
    private SeatService seatService;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Seat Service ] !";
    }

    //分配座位
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/seats")
    public HttpEntity create(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        return ok(seatService.distributeSeat(seatRequest, headers));
    }

    //查询特定区间余票
    @CrossOrigin(origins = "*")
    @PostMapping(value = "/seats/left_tickets")
    public HttpEntity getLeftTicketOfInterval(@RequestBody Seat seatRequest, @RequestHeader HttpHeaders headers) {
        // int
        return ok(seatService.getLeftTicketOfInterval(seatRequest, headers));
    }
}
