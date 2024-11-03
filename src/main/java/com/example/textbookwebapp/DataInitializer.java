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
        if (bookRepository.count() == 0) {
            Book newBook = new Book.BookBuilder()
                    .setIsbn("1234567890")
                    .setAuthor("Author A")
                    .setTitle("Sherlock Holmes")
                    .setEdition("2nd Edition")
                    .setOriginalPrice(100.0)
                    .setAvailable(true)
                    .setTypeId(1)
                    .build();
            
            Book newBook_2 = new Book.BookBuilder()
                    .setIsbn("1234567891")
                    .setAuthor("Author AB")
                    .setTitle("Sherlock Holmes_1")
                    .setEdition("2nd Edition")
                    .setOriginalPrice(200.0)
                    .setAvailable(true)
                    .setTypeId(2)
                    .build();
            
            newBook.setUsedTextBookPrice(0.0); 
            newBook_2.setUsedTextBookPrice(0.0);  // Explicitly set usedTextBookPrice for new books
            bookRepository.save(newBook);
            bookRepository.save(newBook_2);
        }
    }
}
