package adminuser.service;

import adminuser.entity.*;
import edu.fudan.common.util.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class AdminUserServiceImpl implements AdminUserService {
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Response getAllUsers(String id, HttpHeaders headers) {
        FindAllAccountResult result = new FindAllAccountResult();
        if (checkId(id)) {
            System.out.println("[Admin User Service][Get All Users]");
            HttpEntity requestEntity = new HttpEntity(headers);
            ResponseEntity<FindAllAccountResult> re = restTemplate.exchange(
                    "http://ts-sso-service:12349/account/findAll",
                    HttpMethod.GET,
                    requestEntity,
                    FindAllAccountResult.class);
            result = re.getBody();
//            result = restTemplate.getForObject(
//                    "http://ts-sso-service:12349/account/findAll",
//                    FindAllAccountResult.class);
        } else {
            System.out.println("[Admin User Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + id, null);
        }
        ArrayList<Account> accounts = result.getAccountArrayList();
        return new Response<>(1, "Success", accounts);
    }

    @Override
    public Response deleteUser(String loginId, String accountId, HttpHeaders headers) {
        DeleteAccountResult result = new DeleteAccountResult();
        if (checkId(loginId)) {
            AdminDeleteAccountRequest adminDeleteAccountRequest = new AdminDeleteAccountRequest();
            adminDeleteAccountRequest.setAccountId(accountId);
            HttpEntity requestEntity = new HttpEntity(adminDeleteAccountRequest, headers);
            ResponseEntity<DeleteAccountResult> re = restTemplate.exchange(
                    "http://ts-sso-service:12349/account/admindelete",
                    HttpMethod.POST,
                    requestEntity,
                    DeleteAccountResult.class);
            result = re.getBody();
//            result = restTemplate.postForObject(
//                    "http://ts-sso-service:12349/account/admindelete", adminDeleteAccountRequest,DeleteAccountResult.class);
        } else {
            System.out.println("[Admin User Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + loginId, null);
        }

        return new Response<>(1, "Success", result.getAccount());
    }

    @Override
    public Response updateUser(Account request, HttpHeaders headers) {
        ModifyAccountResult result = new ModifyAccountResult();
        if (checkId(request.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<ModifyAccountResult> re = restTemplate.exchange(
                    "http://ts-sso-service:12349/account/modify",
                    HttpMethod.POST,
                    requestEntity,
                    ModifyAccountResult.class);
            result = re.getBody();
//            result = restTemplate.postForObject(
//                    "http://ts-sso-service:12349/account/modify", request.getModifyAccountInfo() ,ModifyAccountResult.class);
        } else {
            System.out.println("[Admin User Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + request.getLoginId(), null);
        }
        return new Response<>(1, result.getMessage(), null);
    }

    @Override
    public Response addUser(Account request, HttpHeaders headers) {
        RegisterResult result = new RegisterResult();
        if (checkId(request.getLoginId())) {
            HttpEntity requestEntity = new HttpEntity(request, headers);
            ResponseEntity<RegisterResult> re = restTemplate.exchange(
                    "http://ts-sso-service:12349/account/register",
                    HttpMethod.POST,
                    requestEntity,
                    RegisterResult.class);
            result = re.getBody();
//            result = restTemplate.postForObject(
//                    "http://ts-sso-service:12349/account/register", request ,RegisterResult.class);
        } else {
            System.out.println("[Admin User Service][Wrong Admin ID]");
            return new Response<>(0, "The loginId is Wrong: " + request.getLoginId(), null);
        }
        return new Response<>(1, result.getMessage(), result.getAccount());
    }

    private boolean checkId(String id) {
        if ("1d1a11c1-11cb-1cf1-b1bb-b11111d1da1f".equals(id)) {
            return true;
        } else {
            return false;
        }
    }
}
