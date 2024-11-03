package com.example.textbookwebapp.strategy;

import com.example.textbookwebapp.entity.Book;

public class NormalWearPricing implements PricingStrategy {
    public double calculatePrice(Book book) {
        return book.getUsedTextBookPrice() * 0.85;
    }
}
