package adminuser.controller;

import adminuser.domain.request.AddAccountRequest;
import adminuser.domain.request.DeleteAccountRequest;
import adminuser.domain.request.UpdateAccountRequest;
import adminuser.domain.response.DeleteAccountResult;
import adminuser.domain.response.FindAllAccountResult;
import adminuser.domain.response.ModifyAccountResult;
import adminuser.domain.response.RegisterResult;
import adminuser.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AdminUserController {
    @Autowired
    AdminUserService adminUserService;

    @CrossOrigin(origins = "*")
    @RequestMapping(path = "/adminuser/findAll/{id}", method = RequestMethod.GET)
    public FindAllAccountResult getAllUsers(@PathVariable String id){
        return adminUserService.getAllUsers(id);
    }

    @RequestMapping(value = "/adminuser/addUser", method= RequestMethod.POST)
    public RegisterResult addUser(@RequestBody AddAccountRequest request){
        return adminUserService.addUser(request);
    }

    @RequestMapping(value = "/adminuser/updateUser", method= RequestMethod.POST)
    public ModifyAccountResult updateOrder(@RequestBody UpdateAccountRequest request){
        return adminUserService.updateUser(request);
    }

    @RequestMapping(value = "/adminuser/deleteUser", method= RequestMethod.POST)
    public DeleteAccountResult deleteOrder(@RequestBody DeleteAccountRequest request){
        return adminUserService.deleteUser(request);
    }
}
