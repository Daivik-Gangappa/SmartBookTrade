package com.example.textbookwebapp.service;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.Transaction;
import com.example.textbookwebapp.repository.BookRepository;
import com.example.textbookwebapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    // Fetch all available books
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }

    // Fetch all books (available and sold)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // Buy a book from a student (update renovation price, keep current price unchanged)
    public String buyBook(Long id, String condition) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (!book.isAvailable()) {
                double usedTextBookPrice = book.getUsedTextBookPrice();
                double renovationPrice = 0.0;

                // Calculate renovation price based on condition
                if ("damaged".equalsIgnoreCase(condition)) {
                    renovationPrice = usedTextBookPrice * 0.20; // 20% reduction
                } else {
                    renovationPrice = usedTextBookPrice * 0.15; // 15% reduction
                }

                // The price at which store buys back the book from the student
                double buyBackPrice = usedTextBookPrice - renovationPrice;

                // Update renovationPrice
                book.setRenovationPrice(renovationPrice);

                // Keep currentPrice unchanged (will be updated during resale)
                // book.setCurrentPrice(usedTextBookPrice);

                // Mark the book as available again
                book.setAvailable(true);

                bookRepository.save(book);

                // Save the transaction
                Transaction transaction = new Transaction(book, null, buyBackPrice, "BUY", LocalDateTime.now());
                transactionRepository.save(transaction);

                return "Book bought from the student and added to inventory!";
            } else {
                return "This book is already in inventory.";
            }
        } else {
            return "Book not found.";
        }
    }

    // Sell a book to a customer (reduce price by 10% from usedTextBookPrice)
    public String sellBook(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (book.isAvailable()) {
                double newSellingPrice = 0.0;

                if (book.getUsedTextBookPrice() == 0.0) {
                    // New book
                    newSellingPrice = book.getOriginalPrice();
                } else {
                    // Used book, decrease price by 10% from usedTextBookPrice
                    newSellingPrice = book.getUsedTextBookPrice() * 0.90;
                }

                // Update currentPrice and usedTextBookPrice
                book.setCurrentPrice(newSellingPrice);
                book.setUsedTextBookPrice(newSellingPrice);

                // Mark as sold
                book.setAvailable(false);

                bookRepository.save(book);

                // Save the transaction
                Transaction transaction = new Transaction(book, null, newSellingPrice, "SELL", LocalDateTime.now());
                transactionRepository.save(transaction);

                return "Book sold to the customer!";
            } else {
                return "This book is already sold.";
            }
        } else {
            return "Book not found.";
        }
    }
}
