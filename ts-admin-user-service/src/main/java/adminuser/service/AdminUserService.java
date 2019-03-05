package adminuser.service;

import adminuser.entity.AddAccountRequest;
import adminuser.entity.DeleteAccountRequest;
import adminuser.entity.UpdateAccountRequest;
import adminuser.entity.DeleteAccountResult;
import adminuser.entity.FindAllAccountResult;
import adminuser.entity.ModifyAccountResult;
import adminuser.entity.RegisterResult;
import org.springframework.http.HttpHeaders;

public interface AdminUserService {
    FindAllAccountResult getAllUsers(String id, HttpHeaders headers);
    DeleteAccountResult deleteUser(DeleteAccountRequest request, HttpHeaders headers);
    ModifyAccountResult updateUser(UpdateAccountRequest request, HttpHeaders headers);
    RegisterResult addUser(AddAccountRequest request, HttpHeaders headers);
}
