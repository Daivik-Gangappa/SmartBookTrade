package com.example.textbookwebapp.strategy;

import com.example.textbookwebapp.entity.Book;

public interface PricingStrategy {
    double calculatePrice(Book book);
}
