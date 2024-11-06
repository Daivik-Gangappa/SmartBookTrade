package com.example.textbookwebapp.strategy;

import com.example.textbookwebapp.entity.Book;

public class NewUsedBookPricing implements PricingStrategy {

    @Override
    public double calculatePrice(Book book) {
        return book.getOriginalPrice() * 0.70;
    }
}
