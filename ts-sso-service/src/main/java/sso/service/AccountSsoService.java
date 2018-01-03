package sso.service;

import sso.domain.*;

public interface AccountSsoService {

    RegisterResult create(RegisterInfo ri);

    Account createAccount(Account account);

    LoginResult login(LoginInfo li);

    PutLoginResult loginPutToken(String loginToken);

    LogoutResult logoutDeleteToken(LogoutInfo li);

    VerifyResult verifyLoginToken(String verifyToken);

    FindAllAccountResult findAllAccount();

    GetLoginAccountList findAllLoginAccount();

    ModifyAccountResult saveChanges(ModifyAccountInfo modifyAccountInfo);

    GetAccountByIdResult getAccountById(GetAccountByIdInfo info);

    Contacts adminLogin(String name, String password);

    DeleteAccountResult deleteAccount(String accountId);

}
