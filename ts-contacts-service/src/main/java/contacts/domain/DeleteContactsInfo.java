package contacts.domain;

public class DeleteContactsInfo {

    private String contactsId;

    /***If you use as Single Service test,you do not need this var, just let it null***/
    private String loginToken;

    public String getContactsId() {
        return contactsId;
    }

    public void setContactsId(String contactsId) {
        this.contactsId = contactsId;
    }

    public String getLoginToken() {
        return loginToken;
    }

    public void setLoginToken(String loginToken) {
        this.loginToken = loginToken;
    }
}
