package com.example.textbookwebapp.controller;

import com.example.textbookwebapp.service.BookService;
import com.example.textbookwebapp.service.BuyBookCommand;
import com.example.textbookwebapp.strategy.PricingStrategy;
import com.example.textbookwebapp.strategy.NormalWearPricing;
import com.example.textbookwebapp.strategy.ExcessiveWearPricing;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.BookType;

@RestController
@RequestMapping("/api/books")
public class BookController {

    @Autowired
    private BookService bookService;
    
    @PostMapping("/add")
    public String addBookToInventory(@RequestBody Book book) {
        return bookService.addNewBookToInventory(book);
    }
    @GetMapping("/inventory")
    public List<Book> getAvailableBooks() {
        return bookService.getAvailableBooks();
    }

    @GetMapping("/all")
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @PostMapping("/buy/{id}")
    public String buyBook(@PathVariable Long id, @RequestParam("condition") String condition) {
        BuyBookCommand buyCommand;
        if ("normal".equalsIgnoreCase(condition)) {
            buyCommand = new BuyBookCommand(bookService, id, new NormalWearPricing());
        } 
        else {
            buyCommand = new BuyBookCommand(bookService, id, new ExcessiveWearPricing());
        }
        String result = buyCommand.execute();
        return result;
    }

    @PostMapping("/buy-used")
    public String sellUsedBook(@RequestBody Book book) {
        return bookService.BuyUsed(book);
    }

    @GetMapping("/type/{type}")
    public List<Book> getBooksByType(@PathVariable BookType type) {
        return bookService.getBooksByType(type);
    }
    
    @GetMapping("/title/{title}")
    public List<Book> getBooksByTitle(@PathVariable String title) {
        return bookService.getBooksByTitle(title);
    }
   
    @PostMapping("/sell/{id}")
    public String sellBook(@PathVariable Long id) {
        PricingStrategy pricingStrategy = new NormalWearPricing();
        return bookService.sellBook(id, pricingStrategy);
    }

    
}
