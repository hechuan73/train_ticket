package ts.trainticket.domain;

public class LoginResult {

    private boolean status;

    private String message;

    private Account account;

    private String token;

    public LoginResult(){
        //Default Constructor
    }

    public LoginResult(boolean status, String message, Account account, String token) {
        this.status = status;
        this.message = message;
        this.account = account;
        this.token = token;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResult{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", account=" + account +
                ", token='" + token + '\'' +
                '}';
    }
}
