package com.example.textbookwebapp;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.BookType;
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
        if (bookRepository.count() == 0) {
            bookRepository.save(new Book.BookBuilder()
                    .setIsbn("1234567890")
                    .setAuthor("Author A")
                    .setTitle("Sherlock Holmes")
                    .setEdition("2nd Edition")
                    .setOriginalPrice(100.0)
                    .setAvailable(true)
                    .setType(BookType.TEXTBOOK)  // Use BookType enum
                    .build());
        }
    }
}
