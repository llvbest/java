package com.company;

import java.math.BigDecimal;
import java.util.Arrays;

public class Account {

    static String result;

    protected static String accountName = "";

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

    protected static String owed = "";

    public static void login(String accountNumber) {
        result = checkAccount(accountNumber);
        if (result.equals("ERROR"))
            return;
        Main.console();
    }
    //behind поведение для инициализации даты например
    //rules - правила валидации []

    public static String checkAccount(String accountNumber) {
        String result = "ERROR";
        /** проверка пароля (md5...) и валидация полей модели, выборка из субд Account.find(accountNumber)
         * и массовое заполнение модели Account */
        Account acc= new Account();
        if (acc.find(accountNumber)) {
            result = "Hello, "+Account.getAccountName()+"!\n";
            result += "Your balance is $"+Account.getBalance();
        } else throw new RuntimeException("Something wrong (first fail)");
        System.out.println(result);
        return result;
    }

    //find Account
    public boolean find(String accountNumber) {
        //пары аккаунтов, т.к не взаимодействуем с бд. пароли одинаковые
        String[] pairs = {"Alice","Bob"};
        boolean contains = Arrays.asList(pairs).contains(accountNumber);
        if(contains) {
            setAccountName(accountNumber);
            Account.balance = new BigDecimal("0.00");
            Account.owed = "";
        }
        //поиск в бд аккаунта по идентификатору уникальному
        //Log.writeDB();
        return contains;
    }
}
