package com.example.textbookwebapp.strategy;

import com.example.textbookwebapp.entity.Book;

public class NewBookPricing implements PricingStrategy {
    public double calculatePrice(Book book) {
        return book.getOriginalPrice();
    }
}
