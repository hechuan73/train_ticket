package adminuser.service;

import adminuser.entity.Account;
import adminuser.entity.DeleteAccountRequest;
import adminuser.entity.UpdateAccountRequest;
import edu.fudan.common.util.Response;
import org.springframework.http.HttpHeaders;

public interface AdminUserService {
    Response getAllUsers(String id, HttpHeaders headers);

    Response deleteUser(String loginId, String accountId, HttpHeaders headers);

    Response updateUser(Account request, HttpHeaders headers);

    Response addUser(Account request, HttpHeaders headers);
}
