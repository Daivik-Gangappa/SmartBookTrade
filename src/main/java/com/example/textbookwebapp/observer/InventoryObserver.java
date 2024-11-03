package com.example.textbookwebapp.observer;

public class InventoryObserver implements Observer {
    public void update(String message) {
        System.out.println("Inventory update: " + message);
    }
}
