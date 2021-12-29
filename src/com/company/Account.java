package com.company;

public class Account {
    //name account
    protected static String accountName = "Alice";
    //name password не в открітом виде, а какой то надежный метод шифрования md5 там и сравнивать хеши
    protected static String password = "12345";
    //balance account
    protected static double balance = 0.00;

    public static double getBalance() {
        return Account.balance;
    }

    public static void setBalance(double balance) {
        Account.balance = balance;
    }

    public static String getAccountName() {
        return Account.accountName;
    }

    public static void setAccountName(String accountName) {
        Account.accountName = accountName;
    }
}
