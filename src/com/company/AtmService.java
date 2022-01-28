package com.company;

import java.math.BigDecimal;

public class AtmService {

    public static void deposit(BigDecimal amount) {
        synchronized(Account.balance) {
            Account.balance = Account.balance.add(amount);
        }
        if(Account.balance.doubleValue() < 0) {
            transfer(Account.owed, amount);
        } else
            System.out.println("Your balance is: " + Account.getBalance());
    }

    //String accountTransfer, Double amountTransfer
    public static void transfer(String accountTransfer, BigDecimal amountTransfer) {
        BigDecimal oldBalance = Account.getBalance();
        /** обернуть все в транцзакции манипуляции с балансами!, у разных СУБД уровни разные, commet если что*/
        synchronized(Account.balance) {
            if(Account.balance.doubleValue() > 0)
                Account.balance = Account.balance.subtract(amountTransfer);
        }
        if(Account.balance.doubleValue() < 0) {
            if(oldBalance.doubleValue() > 0)
                amountTransfer = oldBalance;
        }
        System.out.println("Transferred $" + amountTransfer + " to " + accountTransfer);
        /** Выборка и заполнение accountTransfer класса Account object или интерфейс можно реализовать...
         * и зачисление средств на баланс... */
        if(Account.balance.doubleValue() < 0) {
            Account.owed = accountTransfer;
            System.out.println("Your balance is: " + "0.00");
            System.out.println("Owed $" + Account.getBalance().abs() + "  to  " + accountTransfer);
        } else {
            System.out.println("Your balance is: " + Account.getBalance());
        }
        Log.writeDB();
    }

    public static void withdraw(BigDecimal amount) {
        if( amount.compareTo(Account.getBalance()) < 1 ){//amount <= Account.getBalance()
            /** обернуть все в транцзакцию!, у разных СУБД уровни разные, commet если что*/
            synchronized(Account.balance) {
                Account.balance = Account.balance.subtract(amount);
            }
            System.out.println("Your new balance is: " + Account.getBalance());
            Log.writeDB();
        } else {
            System.out.println("The account had insufficient funds");
        }
    }

    public static int displayBalance() {
        System.out.println("Total balance is: $" + Account.getBalance());
        return 1;
    }
}
