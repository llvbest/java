package com.company;

public class Account {
    //behind поведение для инициализации даты например
    //rules - правила валидации []

    //name account
    protected static String accountName = "Alice";
    //name password не в открытом виде, а какой то надежный метод шифрования md5 там и сравнивать хеши
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

    private static void setAccountName(String accountName) {
        Account.accountName = accountName;
    }

    // например для имени, исключения спец символов, регистр, регулярки, длины имени/пароля ...
    private boolean validate() {
        //запись логов валидации и не|успешной операции
        Log.writeDB();
        return true;// false
    }

    //сохранение в хранилище и правила валидации
    public boolean save() {
        //сохранение в бд и валидация по правилам описанным выше в модели,
        //если аккаунт не найдет тогда INSERT c сгенеренным паролем и показ уведомления клиенту

        //запись логов сохранения и не|успешной операции
        Log.writeDB();
        return true;// false
    }

    //find Account
    public Account find(String accountNumber) {
        //поиск в бд аккаунта по идентификатору уникальному
        Log.writeDB();
        return this;
    }
}
