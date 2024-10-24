package com.example.textbookwebapp;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private BookRepository bookRepository;

    @Override
    public void run(String... args) throws Exception {
        // Create some sample books
        if (bookRepository.count() == 0) {  // Check if the repository is empty
            // When adding books, we specify the original price and current price (after profit)
            bookRepository.save(new Book("1234567890", "Author A", "Title A", "1st Edition", 100.0, 105.0, true));
            bookRepository.save(new Book("0987654321", "Author B", "Title B", "2nd Edition", 150.0, 157.5, true));
            bookRepository.save(new Book("1122334455", "Author C", "Title C", "3rd Edition", 200.0, 210.0, true));

            System.out.println("Database has been initialized with sample data.");
        }
    }
}
