package com.example.textbookwebapp.service;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.BookType;
import com.example.textbookwebapp.observer.Observer;
import com.example.textbookwebapp.repository.BookRepository;
import com.example.textbookwebapp.repository.TransactionRepository;
import com.example.textbookwebapp.strategy.NewUsedBookPricing;
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

    // Fetching all available books in inventory
    public List<Book> getAvailableBooks() {
    	// TODO Auto-generated method stub
        return bookRepository.findByAvailableTrue();
    }

    // Fetching all books in the database (both available and sold)
    public List<Book> getAllBooks() {
    	// TODO Auto-generated method stub
        return bookRepository.findAll();
    }
    
    public List<Book> getBooksByType(BookType type) {
    	// TODO Auto-generated method stub
        return bookRepository.findByType(type);
    }
    public List<Book> getBooksByTitle(String title) {
		// TODO Auto-generated method stub
    	return bookRepository.findByTitleContainingIgnoreCase(title);
	}
    
    // Add a new book to the inventory
    public String addNewBookToInventory(Book book) {
        book.setCurrentPrice(book.getOriginalPrice());  // Current price=original price for new books
        book.setAvailable(true);
        book.setUsedTextBookPrice(0.0); // UsedTextBookPrice is assigned with 0 and gets updated after the first sell
        bookRepository.save(book);
        notifyObservers("New book added to inventory with ID: " + book.getId());
        return "New book added to inventory!";
    } 
    
    public String BuyUsed(Book book) {
        Optional<Book> existingBook = bookRepository.findByIsbn(book.getIsbn());

        if (existingBook.isPresent()) {
            return "This book is already in inventory.";
        } else {
            // 30% reduction  to calculate the buyback price using NewUsedBookPricing strategy
            PricingStrategy buybackStrategy = new NewUsedBookPricing();
            double buybackPrice = buybackStrategy.calculatePrice(book);

            // Set currentPrice  to the buyback price and usedTextBookPrice to 0
            book.setUsedTextBookPrice(0.0);
            book.setCurrentPrice(buybackPrice);
            book.setRenovationPrice(book.getOriginalPrice() * 0.30); // renovationPrice as the 30% reduction amount
            book.setAvailable(true);
            bookRepository.save(book);// Save the new book to inventory

            notifyObservers("Used book added to inventory with ID: " + book.getId());
            return "Book added to inventory as a used book! Buyback price: " + buybackPrice;
        }
    }
    
    public String buyBook(Long id, PricingStrategy strategy) {
        Optional<Book> optionalBook = bookRepository.findById(id);

        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (book.isAvailable()) {
                return "Book is already in inventory.";
            }

            // strategy to calculate the renovation charge
            double renovationCharge = book.getUsedTextBookPrice() - strategy.calculatePrice(book);
            book.setRenovationPrice(renovationCharge);            
            book.setCurrentPrice(book.getUsedTextBookPrice() * 0.90); // current price with a 10% reduction for resale            
            book.setAvailable(true);// Mark the book as available in inventory
            bookRepository.save(book);// updating details to repository
            notifyObservers("Book bought back from student with ID: " + id);
            return "Book bought back from student and added to inventory!";
        }

        return "Book not found.";
    }

    public String sellBook(Long id, PricingStrategy strategy) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();

            if (book.isAvailable()) {
                double sellPrice = strategy.calculatePrice(book); // Calculating the sell price based on the pricing strategy
                book.setCurrentPrice(sellPrice);// Set the current price for the sale

                // Updating the usedTextBookPrice to the last selling price
                if (book.getUsedTextBookPrice() == 0.0) {
                    book.setUsedTextBookPrice(book.getOriginalPrice()); // First-time sale
                }
                else {
                    book.setUsedTextBookPrice(book.getCurrentPrice()); // Subsequent sales
                }
                book.setAvailable(false);
                bookRepository.save(book);
                notifyObservers("Book sold with ID: " + id);
                return "Book sold at price: " + sellPrice;
            } 
            else {
                return "Book is not available for sale.";
            }
        }
        return "Book not found.";
    }
}
