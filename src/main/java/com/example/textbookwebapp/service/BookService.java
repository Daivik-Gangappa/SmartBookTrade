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
        return bookRepository.findByAvailableTrue();  // Shows only available books
    }

    // Buy a book from a customer and reduce price based on condition
    public String buyBook(Long id, String condition) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent() && bookOpt.get().isAvailable()) {
            Book book = bookOpt.get();
            double price = book.getOriginalPrice();

            // Apply price reduction based on the condition of the book
            if ("normal".equalsIgnoreCase(condition)) {
                price = price - (price * 0.10);  // 10% reduction for regular/no damage
            } else if ("damaged".equalsIgnoreCase(condition)) {
                price = price - (price * 0.20);  // 20% reduction for severe damage
            }

            book.setOriginalPrice(price);  // Set the reduced price after purchasing
            book.setAvailable(false);      // Mark the book as sold
            bookRepository.save(book);

            return "Book has been bought with reduced price based on condition!";
        } else {
            return "Book not available or already sold!";
        }
    }

    // Sell a book to a customer with 5% profit added
    public String sellBook(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent() && !bookOpt.get().isAvailable()) {
            Book book = bookOpt.get();
            double price = book.getOriginalPrice();
            
            // Add 5% profit to the original price
            double sellingPrice = price + (price * 0.05);
            book.setCurrentPrice(sellingPrice);  // Set the price after renovation (with profit)
            book.setAvailable(true);             // Make the book available for sale again
            bookRepository.save(book);

            return "Book has been sold with 5% profit!";
        } else {
            return "Book not found or already available!";
        }
    }
}
