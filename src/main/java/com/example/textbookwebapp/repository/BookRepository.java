package com.example.textbookwebapp.repository;

import com.example.textbookwebapp.entity.Book;
import com.example.textbookwebapp.entity.BookType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailableTrue();  // Finds books that are marked as available
    List<Book> findByType(BookType type);  // Find books by type
	List<Book> findByTitleContainingIgnoreCase(String title);
}
