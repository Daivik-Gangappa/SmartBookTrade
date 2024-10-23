package com.example.textbookwebapp.service;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.Transaction;
import com.example.textbookwebapp.repository.BookRepository;
import com.example.textbookwebapp.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    // Buy a book by ID
    public String buyBook(Long id) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        if (bookOpt.isPresent() && bookOpt.get().isAvailable()) {
            Book book = bookOpt.get();
            book.setAvailable(false);
            bookRepository.save(book);

            // Save transaction
            Transaction transaction = new Transaction(book, book.getPrice());
            transactionRepository.save(transaction);

            return "Book bought successfully!";
        } else {
            return "Book not available!";
        }
    }

    // Sell a book
    public String sellBook(Book book) {
        book.setAvailable(true);
        bookRepository.save(book);
        return "Book sold successfully!";
    }
}
