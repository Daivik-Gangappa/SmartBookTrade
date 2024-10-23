package com.example.textbookwebapp.controller;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    // Buy a book by ID
    @PostMapping("/buy/{id}")
    public String buyBook(@PathVariable Long id) {
        return bookService.buyBook(id);
    }

    // Sell a book
    @PostMapping("/sell")
    public String sellBook(@RequestBody Book book) {
        return bookService.sellBook(book);
    }
}
