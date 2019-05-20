package notification.controller;

import notification.entity.NotifyInfo;
import notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Wenyi on 2017/6/15.
 */
@RestController
@RequestMapping("/api/v1/notifyservice")
public class NotificationController {

    @Autowired
    NotificationService service;

    @GetMapping(path = "/welcome")
    public String home() {
        return "Welcome to [ Notification Service ] !";
    }

    @PostMapping(value = "/notification/preserve_success")
    public boolean preserve_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.preserve_success(info, headers);
    }

    @PostMapping(value = "/notification/order_create_success")
    public boolean order_create_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.order_create_success(info, headers);
    }

    @PostMapping(value = "/notification/order_changed_success")
    public boolean order_changed_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.order_changed_success(info, headers);
    }

    @PostMapping(value = "/notification/order_cancel_success")
    public boolean order_cancel_success(@RequestBody NotifyInfo info, @RequestHeader HttpHeaders headers) {
        return service.order_cancel_success(info, headers);
    }
}
