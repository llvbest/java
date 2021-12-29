package com.company;
import java.lang.System;
import java.util.Arrays;
import java.util.Scanner;
import java.security.*;

import static java.lang.Math.abs;

public class Main {
    static Scanner keyboard = new Scanner(System.in);
    static String accountNumber, accountPassword, result;
    static int choose;
    static double oldBalance, newBalance;

    public static void main(String[] args) {
	    //welcome
        System.out.println("Welcome, customer!");

        for (int run = 0; run < 3; run++) {
            System.out.println("Enter your account number:");
            accountNumber = keyboard.nextLine();
            System.out.println("Enter your account password:");
            accountPassword = keyboard.nextLine();

            result = checkAccount(accountNumber, accountPassword);
            if (!result.equals("ERROR")) {
                break;
            } else if (run == 2) {// you cannot try to log in anymore than times
                System.out.println("MAXIMUM TRIES EXCEEDED");
                return;
            }

        }
        menu();
    }

    public static String checkAccount(String accountNumber, String accountPassword) {
        String result = "ERROR";
        /*try {
            byte[] bytesOfMessage = accountPassword.getBytes("UTF-8");
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] theDigest = md.digest(bytesOfMessage);
        } catch (java.io.UnsupportedEncodingException e){
            System.out.println("FAIL");
            return "void";
        }*/
        /** проверка пароля (md5...) и валидация полей модели, выборка из субд Account.find(accountNumber)
         * и массовое заполнение модели Account */
        if (accountNumber.equals(Account.accountName) && accountPassword.equals(Account.password)) {
            result = "Welcome ATM! Glad to see you, Dear "+Account.getAccountName()+"!\n";
            result += "Your balance is $"+Account.getBalance();
        }
        System.out.println(result);
        return result;
    }

    public static int menu() {
        System.out.println("-------------------------------------------" +
                "\nChoose one of the following: \n1.Display Balance\n2.Deposit\n3.Withdraw\n4.Transfer\n5.Log Out");
        choose = keyboard.nextInt();

        switch (choose) {
            case 1:  displayBalance();
                menu();
                break;
            case 2:  deposit();
                menu();
                break;
            case 3:  withdraw();
                menu();
                break;
            case 4:  transfer();
                menu();
                break;
        }

        if (choose == 5) {// 4. Log out
            System.out.println("Goodbue. You are logged out.");
            return 5;
        }

        if (choose <= 6) {// type in anything greater than 4 and you will get a system error
            System.out.println("System Error");
            menu();
            return 6;
        }

        if (choose >= 1) {// type in anything less than 1 and you will get a system error
            System.out.println("System Error");
            menu();
            return 7;
        }
        return choose;
    }

    public static void deposit()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter deposit amount:");
        double amount = Math.abs(input.nextDouble());
        System.out.println("Your deposit amount: " + amount);
        Account.balance += amount;
        System.out.println("Your balance is: " + Account.getBalance());
    }

    public static double displayBalance() {
        System.out.println("Total balance is: $" + Account.getBalance());
        oldBalance = 0.00;
        return 1;
    }

    public static void withdraw()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter withdrawal amount: ");
        double amount = Math.abs(input.nextDouble());
        System.out.println("Your withdrawal amount: " + amount);
        if(amount <= Account.getBalance()){
            /** обернуть все в транцзакцию!, у разных СУБД уровни разные, commet если что*/
            Account.balance -= amount;
            System.out.println("Your new balance is: " + Account.getBalance());
            Log.writeDB();
        } else {
            System.out.println("The account had insufficient funds");
        }
    }

    //String accountTransfer, Double amountTransfer
    public static void transfer()
    {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter account target name transfer:");
        String accountTransfer = input.nextLine();
        System.out.println("Enter amount transfer:");
        double amountTransfer = Math.abs(input.nextDouble());

        /** обернуть все в транцзакции манипуляции с балансами!, у разных СУБД уровни разные, commet если что*/
        Account.balance -= amountTransfer;
        //Account.save();
        //with getter method
        //AccountTarget.setBalance(AccountTarget.getBalance()+amountTransfer);
        //AccountTarget.save();
        System.out.println("Transferred $" + amountTransfer + "to " + accountTransfer);
        /** Выборка и заполнение accountTransfer класса Account object или интерфейс можно реализовать...
         * и зачисление средств на баланс... */
        System.out.println("Your new balance is: " + Account.getBalance());
        if(Account.balance < 0)
            System.out.println("Owned $" + Math.abs(Account.getBalance()) + "  to  " + accountTransfer);

        Log.writeDB();
    }
}