package adminuser.service;

import adminuser.domain.request.AddAccountRequest;
import adminuser.domain.request.DeleteAccountRequest;
import adminuser.domain.request.UpdateAccountRequest;
import adminuser.domain.response.DeleteAccountResult;
import adminuser.domain.response.FindAllAccountResult;
import adminuser.domain.response.ModifyAccountResult;
import adminuser.domain.response.RegisterResult;

public interface AdminUserService {
    FindAllAccountResult getAllUsers(String id);
    DeleteAccountResult deleteUser(DeleteAccountRequest request);
    ModifyAccountResult updateUser(UpdateAccountRequest request);
    RegisterResult addUser(AddAccountRequest request);
}
