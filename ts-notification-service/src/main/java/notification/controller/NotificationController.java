package notification.controller;

import notification.domain.NotifyInfo;
import notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class NotificationController {
    @Autowired
    NotificationService service;

    @RequestMapping(value="/notification/preserve_success", method = RequestMethod.POST)
    public boolean preserve_success(@RequestBody NotifyInfo info){
        return service.preserve_success(info);
    }

    @RequestMapping(value="/notification/order_create_success", method = RequestMethod.POST)
    public boolean order_create_success(@RequestBody NotifyInfo info){
        return service.order_create_success(info);
    }

    @RequestMapping(value="/notification/order_changed_success", method = RequestMethod.POST)
    public boolean order_changed_success(@RequestBody NotifyInfo info){
        return service.order_changed_success(info);
    }

    @RequestMapping(value="/notification/order_cancel_success", method = RequestMethod.POST)
    public boolean order_cancel_success(@RequestBody NotifyInfo info){
        return service.order_cancel_success(info);
    }
}
