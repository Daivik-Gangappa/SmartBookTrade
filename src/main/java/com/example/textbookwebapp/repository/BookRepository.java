package com.example.textbookwebapp.repository;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailableTrue();  // Finding books that are marked as available
    List<Book> findByType(BookType type);  // Find books by specific type
	List<Book> findByTitleContainingIgnoreCase(String title); // Find books by title/part of title
	Optional<Book> findByIsbn(String isbn); // find book by isbn 
}
