package com.example.textbookwebapp.service;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    // Fetch all available books
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }

    // Fetch all books (available and sold)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Buy a book (reduce price based on condition)
    public String buyBook(Long id, String condition) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isAvailable()) {
                if ("normal".equalsIgnoreCase(condition)) {
                    book.setOriginalPrice(book.getOriginalPrice() * 0.90);  // Reduce price by 10%
                } else if ("damaged".equalsIgnoreCase(condition)) {
                    book.setOriginalPrice(book.getOriginalPrice() * 0.80);  // Reduce price by 20%
                } else {
                    return "Invalid condition. Use 'normal' or 'damaged'.";
                }
                book.setAvailable(false);  // Mark as sold
                bookRepository.save(book);
                return "Book has been bought with reduced price based on condition!";
            } else {
                return "This book is already sold.";
            }
        } else {
            return "Book not found.";
        }
    }

    // Sell a book (add 5% profit)
    public String sellBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!book.isAvailable()) {
                book.setCurrentPrice(book.getCurrentPrice() * 1.05);  // Add 5% profit
                book.setAvailable(true);  // Mark as available
                bookRepository.save(book);
                return "Book has been sold with 5% profit!";
            } else {
                return "This book is already available for sale.";
            }
        } else {
            return "Book not found.";
        }
    }
}
