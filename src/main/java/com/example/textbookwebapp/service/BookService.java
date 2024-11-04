package com.example.textbookwebapp.service;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.BookType;
import com.example.textbookwebapp.observer.Observer;
import com.example.textbookwebapp.repository.BookRepository;
import com.example.textbookwebapp.repository.TransactionRepository;
import com.example.textbookwebapp.strategy.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    private List<Observer> observers = new ArrayList<>();

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers(String message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }

    // Method to fetch all available books in inventory
    public List<Book> getAvailableBooks() {
        return bookRepository.findByAvailableTrue();
    }

    // Method to fetch all books in the database (both available and sold)
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }
 // In BookService.java
    public List<Book> getBooksByType(BookType type) {
        return bookRepository.findByType(type);
    }
    public List<Book> getBooksByTitle(String title) {
		// TODO Auto-generated method stub
    	return bookRepository.findByTitleContainingIgnoreCase(title);
	}
    // Add a new book to the inventory
    public String addNewBookToInventory(Book book) {
        book.setCurrentPrice(book.getOriginalPrice());  // Set current price to original price for new book
        book.setAvailable(true);
        // Ensure usedTextBookPrice is not set initially
        book.setUsedTextBookPrice(0.0);
        bookRepository.save(book);
        notifyObservers("New book added to inventory with ID: " + book.getId());
        return "New book added to inventory!";
    } 

    // Buy a book back from a customer, adjusting renovation and current price
    public String buyBook(Long id, PricingStrategy strategy) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (!book.isAvailable()) {
                double renovationCharge;
                // Calculate the renovation charge based on the strategy
                renovationCharge =book.getUsedTextBookPrice()- strategy.calculatePrice(book);

                // Set the renovation price
                book.setRenovationPrice(renovationCharge);

                // Calculate the new current price for resale (10% reduction)
                book.setCurrentPrice(book.getUsedTextBookPrice() * 0.90);

                // Mark the book as available in inventory
                book.setAvailable(true);

                // Save the updated book details to the repository
                bookRepository.save(book);
                notifyObservers("Book bought back from student with ID: " + id);
                return "Book bought back from student and added to inventory!";
            } else {
                return "Book is already in inventory.";
            }
        }
        return "Book not found.";
    }

    public String sellBook(Long id, PricingStrategy strategy) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (book.isAvailable()) {
                double sellPrice = strategy.calculatePrice(book);

                // Update the usedTextBookPrice to the last selling price (for future buybacks)
                if (book.getUsedTextBookPrice() == 0.0) {
                    book.setUsedTextBookPrice(book.getOriginalPrice()); // First time sale
                } else {
                    book.setUsedTextBookPrice(book.getCurrentPrice()); // Subsequent sales
                }

                // Set the current price for the sale
                book.setCurrentPrice(sellPrice);

                // Mark the book as sold
                book.setAvailable(false);

                // Save the updated book details to the repository
                bookRepository.save(book);
                notifyObservers("Book sold with ID: " + id);
                return "Book sold!";
            } else {
                return "Book is not available for sale.";
            }
        }
        return "Book not found.";
    }

	

}
