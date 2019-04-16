package com.trainticket.entity;

import org.springframework.data.mongodb.core.mapping.Document;


@Document(collection="addMoney")
public class Money {
    private String userId;
    private String money;


    public Money(){}

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
