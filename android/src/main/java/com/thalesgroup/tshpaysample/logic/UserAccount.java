package com.thalesgroup.tshpaysample.logic;

public class UserAccount {
    private static UserAccount dbObject;
    String account;
    private UserAccount() {
    }
    public static UserAccount getInstance() {

        if (dbObject == null) {
            dbObject = new UserAccount();
        }

        return dbObject;
    }
    public void setAccount(String value) {
        account = value;
    }
    public String getAccount() {
        return account;
    }
    }
