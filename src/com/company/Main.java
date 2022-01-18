package com.company;
import java.lang.System;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static Scanner keyboard = new Scanner(System.in);
    static String accountNumber, accountPassword, result;
    static int choose;

    public static void main(String[] args) {
        try {
            login();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void login() {
        //tries
        for (int run = 0; run < 3; run++) {
            System.out.println("\nEnter your account number:");
            accountNumber = keyboard.next();
            System.out.println("Enter your account password:");
            accountPassword = keyboard.next();

            result = checkAccount(accountNumber, accountPassword);
            if (!result.equals("ERROR")) {
                break;
            } else if (run == 2) {
                System.out.println("MAXIMUM TRIES EXCEEDED");
                return;
            }
        }
        menu();
    }

    public static String checkAccount(String accountNumber, String accountPassword) {
        String result = "ERROR";
        /** проверка пароля (md5...) и валидация полей модели, выборка из субд Account.find(accountNumber)
         * и массовое заполнение модели Account */
        Account acc= new Account();
        if (acc.find(accountNumber) && accountPassword.equals(Account.password)) {
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

        if (choose == 5) {// 5. Log out
            System.out.println("Goodbue, "+Account.getAccountName());
            login();
            return 5;
        }

        if (choose <= 6) {
            System.out.println("System Error");
            menu();
            return 6;
        }

        if (choose >= 1) {
            System.out.println("System Error");
            menu();
            return 7;
        }
        return choose;
    }

    public static void deposit() {
        System.out.println("Enter deposit amount:");
        BigDecimal amount = BigDecimal.valueOf(Math.abs(keyboard.nextDouble()));
        System.out.println("Your deposit amount: " + amount);
        Account.balance = Account.balance.add(amount);
        System.out.println("Your balance is: " + Account.getBalance());
    }

    public static int displayBalance() {
        System.out.println("Total balance is: $" + Account.getBalance());
        return 1;
    }

    public static void withdraw() {
        System.out.println("Enter withdrawal amount: ");
        BigDecimal amount = BigDecimal.valueOf(Math.abs(keyboard.nextDouble()));
        System.out.println("Your withdrawal amount: " + amount);
        if( amount.compareTo(Account.getBalance()) < 1 ){//amount <= Account.getBalance()
            /** обернуть все в транцзакцию!, у разных СУБД уровни разные, commet если что*/
            Account.balance = Account.balance.subtract(amount);
            System.out.println("Your new balance is: " + Account.getBalance());
            Log.writeDB();
        } else {
            System.out.println("The account had insufficient funds");
        }
    }

    //String accountTransfer, Double amountTransfer
    public static void transfer() {
        System.out.println("Enter account target name transfer:");
        String accountTransfer = keyboard.nextLine();
        System.out.println("Enter amount transfer:");
        BigDecimal amountTransfer = BigDecimal.valueOf(Math.abs(keyboard.nextDouble()));

        /** обернуть все в транцзакции манипуляции с балансами!, у разных СУБД уровни разные, commet если что*/
        Account.balance = Account.balance.subtract(amountTransfer);

        System.out.println("Transferred $" + amountTransfer + "to " + accountTransfer);
        /** Выборка и заполнение accountTransfer класса Account object или интерфейс можно реализовать...
         * и зачисление средств на баланс... */
        System.out.println("Your new balance is: " + Account.getBalance());
        if(Account.balance.doubleValue() < 0)
            System.out.println("Owned $" + Account.getBalance() + "  to  " + accountTransfer);

        Log.writeDB();
    }
}