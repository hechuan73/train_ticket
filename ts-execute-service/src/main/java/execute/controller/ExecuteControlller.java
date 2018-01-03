package execute.controller;

import execute.domain.TicketExecuteInfo;
import execute.domain.TicketExecuteResult;
import execute.serivce.ExecuteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ExecuteControlller {

    @Autowired
    private ExecuteService executeService;

    @RequestMapping(path = "/welcome", method = RequestMethod.GET)
    public String home() {
        return "Welcome to [ Execute Service ] !";
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/execute/execute", method = RequestMethod.POST)
    public TicketExecuteResult executeTicket(@RequestBody TicketExecuteInfo info){
        System.out.println("[Execute Service][Execute] Id:" + info.getOrderId());
        return executeService.ticketExecute(info);
    }

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/execute/collected", method = RequestMethod.POST)
    public TicketExecuteResult collectTicket(@RequestBody TicketExecuteInfo info){
        System.out.println("[Execute Service][Collect] Id:" + info.getOrderId());
        return executeService.ticketCollect(info);
    }
}
