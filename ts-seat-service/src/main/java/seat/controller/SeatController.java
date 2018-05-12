package seat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import seat.domain.SeatRequest;
import seat.domain.Ticket;
import seat.service.SeatService;

@RestController
public class SeatController {

    @Autowired
    private SeatService seatService;


    @CrossOrigin(origins = "*")
    @RequestMapping(value="/seat/getSeat", method= RequestMethod.POST)
    public Ticket create(@RequestBody SeatRequest seatRequest){
        return seatService.distributeSeat(seatRequest);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(value="/seat/getLeftTicketOfInterval", method= RequestMethod.POST)
    public int getLeftTicketOfInterval(@RequestBody SeatRequest seatRequest){
        return seatService.getLeftTicketOfInterval(seatRequest);
    }
}
