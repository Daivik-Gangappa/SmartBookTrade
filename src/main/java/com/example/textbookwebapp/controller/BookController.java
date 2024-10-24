package com.example.textbookwebapp.controller;

import com.example.textbookwebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.example.textbookwebapp.entity.Book;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;

    // Get all available books
    @GetMapping("/inventory")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    // Buy a book from a customer (condition: normal/damaged)
    @PostMapping("/buy/{id}")
    public String buyBook(@PathVariable Long id, @RequestParam("condition") String condition) {
        return bookService.buyBook(id, condition);
    }

    // Sell a book to a customer (add profit)
    @PostMapping("/sell/{id}")
    public String sellBook(@PathVariable Long id) {
        return bookService.sellBook(id);
    }
}
