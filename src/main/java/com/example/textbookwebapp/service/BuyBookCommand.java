package com.example.textbookwebapp.service;

import com.example.textbookwebapp.strategy.ExcessiveWearPricing;
import com.example.textbookwebapp.strategy.NormalWearPricing;
import com.example.textbookwebapp.strategy.PricingStrategy;

public class BuyBookCommand implements Command {
    private final BookService bookService;
    private final Long bookId;
    private final PricingStrategy pricingStrategy;

    public BuyBookCommand(BookService bookService, Long bookId, PricingStrategy pricingStrategy) {
        this.bookService = bookService;
        this.bookId = bookId;
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public String execute() {
        return bookService.buyBook(bookId, pricingStrategy);// bookService method and return its result
    }
}


