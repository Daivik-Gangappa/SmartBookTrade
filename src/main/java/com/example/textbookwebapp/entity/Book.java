package com.example.textbookwebapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "books")
@JsonIgnoreProperties({"transactions"})
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String isbn;
    private String author;
    private String title;
    private String edition;

    // New fields
    private double originalPrice;     // Price of new book
    private double currentPrice;      // Selling price after renovation
    private double usedTextBookPrice; // Price when buying a used book from a student
    private double renovationPrice;   // Reduction price based on condition

    private boolean available;        // Availability status (true if available for sale)

    private Long typeId;              // Identifier for grouping similar book types

    // Transactions related to this book
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> transactions;

    // Constructors, Getters, and Setters
    public Book() {}

    public Book(String isbn, String author, String title, String edition, double originalPrice, double currentPrice, double usedTextBookPrice, double renovationPrice, boolean available, Long typeId) {
        this.isbn = isbn;
        this.author = author;
        this.title = title;
        this.edition = edition;
        this.originalPrice = originalPrice;
        this.currentPrice = currentPrice;
        this.usedTextBookPrice = usedTextBookPrice;
        this.renovationPrice = renovationPrice;
        this.available = available;
        this.typeId = typeId;
    }

    // Add all necessary getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }

    public double getOriginalPrice() {
        return originalPrice;
    }

    public void setOriginalPrice(double originalPrice) {
        this.originalPrice = originalPrice;
    }

    public double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public double getUsedTextBookPrice() {
        return usedTextBookPrice;
    }

    public void setUsedTextBookPrice(double usedTextBookPrice) {
        this.usedTextBookPrice = usedTextBookPrice;
    }

    public double getRenovationPrice() {
        return renovationPrice;
    }

    public void setRenovationPrice(double renovationPrice) {
        this.renovationPrice = renovationPrice;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Long getTypeId() {
        return typeId;
    }

    public void setTypeId(Long typeId) {
        this.typeId = typeId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }
}
