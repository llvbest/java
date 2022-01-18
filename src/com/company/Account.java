package com.company;

import java.math.BigDecimal;
import java.util.Arrays;

public class Account {
    //behind поведение для инициализации даты например
    //rules - правила валидации []

    protected static String accountName = "";
    //name password не в открытом виде, а какой то надежный метод шифрования md5 там и сравнивать хеши
    protected static String password = "12345";

    protected static BigDecimal balance = new BigDecimal("0.00");

    public static BigDecimal getBalance() {
        return Account.balance;
    }

    public void setBalance(BigDecimal balance) {
        Account.balance = balance;
    }

    public static String getAccountName() {
        return Account.accountName;
    }

    private void setAccountName(String accountName) {
        Account.accountName = accountName;
    }

    //find Account
    public boolean find(String accountNumber) {
        //пары аккаунтов, т.к не взаимодействуем с бд. пароли одинаковые
        String[] pairs = {"Alice","Bob"};
        boolean contains = Arrays.asList(pairs).contains(accountNumber);
        if(contains) {
            setAccountName(accountNumber);
        }
        //поиск в бд аккаунта по идентификатору уникальному
        //Log.writeDB();
        return contains;
    }
}
