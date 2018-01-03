package com.trainticket.domain;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Administrator on 2017/6/23.
 */
@Document(collection="addMoney")
public class AddMoney {
    private String userId;
    private String money;


    public AddMoney(){}

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }
}
