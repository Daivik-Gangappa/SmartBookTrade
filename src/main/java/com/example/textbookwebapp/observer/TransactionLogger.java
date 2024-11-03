package com.example.textbookwebapp.observer;

public class TransactionLogger implements Observer {
    public void update(String message) {
        System.out.println("Transaction log: " + message);
    }
}
