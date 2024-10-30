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
        // Add sample books to the database
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book("1234567890", "Author A", "Sherlock Holmes", "2nd Edition", 100.0, 100.0, 00.0, 0, true, 1L));
            bookRepository.save(new Book("0987654321", "Author B", "Moby Dick", "1st Edition", 150.0, 150.0, 00.0, 0, true, 2L));
        }
    }
}
