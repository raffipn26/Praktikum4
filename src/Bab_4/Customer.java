package Bab_4;

import java.util.Scanner;

public class Customer {
    private String customerNumber;
    private String name;
    private double balance;
    private String pin;
    private boolean blocked = false;
    private int failedAttempts = 0;

    public Customer(String customerNumber, String name, double balance, String pin) {
        this.customerNumber = customerNumber;
        this.name = name;
        this.balance = balance;
        this.pin = pin;
    }

    public String getCustomerNumber() {
        return customerNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public boolean authenticateByPin(String inputPin) {
        if (blocked) return false;
    
        if (this.pin.equals(inputPin)) {
            failedAttempts = 0;
            return true;
        } else {
            failedAttempts++;
            if (failedAttempts >= 3) blocked = true;
            return false;
        }
    }
    

    private double getCashback(double amount) {
        if (amount <= 1000000) return 0;
        String prefix = customerNumber.substring(0, 2);
        switch (prefix) {
            case "38":
                return amount * 0.05;
            case "56":
                return amount * 0.07;
            case "74":
                return amount * 0.10;
            default:
                return 0;
        }
    }

    private double getSmallCashback(double amount) {
        if (amount > 1000000) return 0;
        String prefix = customerNumber.substring(0, 2);
        switch (prefix) {
            case "56":
                return amount * 0.02;
            case "74":
                return amount * 0.05;
            default:
                return 0;
        }
    }

    public boolean purchase(double amount) {
        double cashback = getCashback(amount) + getSmallCashback(amount);
        double finalAmount = amount - cashback;
        if (balance - finalAmount < 10000) return false;
        balance -= finalAmount;
        return true;
    }

    public boolean topUp(double amount) {
        if (amount <= 0) return false;
        balance += amount;
        return true;
    }
}
