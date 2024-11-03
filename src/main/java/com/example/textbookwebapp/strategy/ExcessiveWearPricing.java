package com.example.textbookwebapp.strategy;

import com.example.textbookwebapp.entity.Book;

public class ExcessiveWearPricing implements PricingStrategy {
    public double calculatePrice(Book book) {
        return book.getUsedTextBookPrice() * 0.80;
    }
}
