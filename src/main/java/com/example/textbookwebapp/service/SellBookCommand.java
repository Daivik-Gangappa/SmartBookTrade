package com.example.textbookwebapp.service;

import com.example.textbookwebapp.strategy.PricingStrategy;

public class SellBookCommand implements Command {
    private final BookService bookService;
    private final Long bookId;
    private final PricingStrategy pricingStrategy;

    public SellBookCommand(BookService bookService, Long bookId, PricingStrategy pricingStrategy) {
        this.bookService = bookService;
        this.bookId = bookId;
        this.pricingStrategy = pricingStrategy;
    }

    @Override
    public String execute() {
        return bookService.sellBook(bookId, pricingStrategy);
    }
}
