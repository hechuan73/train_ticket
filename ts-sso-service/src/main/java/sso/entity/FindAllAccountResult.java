package sso.entity;

import java.util.ArrayList;
import java.util.List;

public class FindAllAccountResult {

    private boolean status;

    private String message;

    private List<Account> accountArrayList;

    public FindAllAccountResult() {
        //Default Constructor
    }

    public boolean isStatus() {
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

    public List<Account> getAccountArrayList() {
        return accountArrayList;
    }

    public void setAccountArrayList(List<Account> accountArrayList) {
        this.accountArrayList = accountArrayList;
    }
}
