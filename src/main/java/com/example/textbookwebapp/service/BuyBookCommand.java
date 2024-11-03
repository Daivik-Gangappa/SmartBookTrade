package com.example.textbookwebapp.service;

import com.example.textbookwebapp.strategy.PricingStrategy;

public class BuyBookCommand implements Command {
    private BookService bookService;
    private Long bookId;
    private PricingStrategy strategy;

    public BuyBookCommand(BookService bookService, Long bookId, PricingStrategy strategy) {
        this.bookService = bookService;
        this.bookId = bookId;
        this.strategy = strategy;
    }

    @Override
    public void execute() {
        bookService.buyBook(bookId, strategy);
    }
}
