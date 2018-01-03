package rebook.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import rebook.domain.RebookInfo;
import rebook.domain.RebookResult;
import rebook.service.RebookService;

@RestController
public class RebookController {

    @Autowired
    RebookService service;

    @RequestMapping(value="/rebook/payDifference", method = RequestMethod.POST)
    public RebookResult payDifference(@RequestBody RebookInfo info, @CookieValue String loginId, @CookieValue String loginToken){
        return service.payDifference(info, loginId, loginToken);
    }

    @RequestMapping(value="/rebook/rebook", method = RequestMethod.POST)
    public RebookResult rebook(@RequestBody RebookInfo info, @CookieValue String loginId, @CookieValue String loginToken){
        System.out.println("[Rebook Service] OrderId:" + info.getOrderId() + "Old Trip Id:" + info.getOldTripId() + " New Trip Id:" + info.getTripId() + " Date:" + info.getDate() + " Seat Type:" + info.getSeatType());
        return service.rebook(info, loginId, loginToken);
    }
}
