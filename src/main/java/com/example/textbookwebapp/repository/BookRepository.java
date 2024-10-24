package com.example.textbookwebapp.repository;

import com.example.textbookwebapp.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByAvailableTrue(); // Fetch all books that are available
}
