package com.company;
import java.lang.System;
import java.math.BigDecimal;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Main {
    static Scanner keyboard = new Scanner(System.in);
    static String accountNumber, result;
    static String[] params;
    static int choose;
    static BigDecimal amount;

    public static void main(String[] args) {
        try {
            console();
        } catch (NoSuchElementException e) {
            System.err.println(e.getMessage());
        } catch (IllegalStateException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void console() {
        params = keyboard.nextLine().trim().split(" ");
        if ("login".equals(params[0])) {
            accountNumber = params[1];
            Account.login(accountNumber.trim());
            console();
        } else if ("deposit".equals(params[0])) {
            BigDecimal amount = new BigDecimal(params[1]);
            AtmService.deposit(amount.abs());
            console();
        } else if ("withdraw".equals(params[0])) {
            BigDecimal amount = new BigDecimal(params[1]);
            AtmService.withdraw(amount.abs());
            console();
        } else if ("transfer".equals(params[0])) {
            String accountTransfer = params[1];
            BigDecimal amountTransfer = new BigDecimal(params[2]);
            AtmService.transfer(accountTransfer, amountTransfer.abs());
            console();
        } else if ("logout".equals(params[0])) {
            if(Account.getAccountName() == ""){
                System.out.println("Please first login");
            } else
                System.out.println("Goodbye, "+Account.getAccountName());
            console();
        } else if ("balance".equals(params[0])) {
            AtmService.displayBalance();
            console();
        }
    }
}