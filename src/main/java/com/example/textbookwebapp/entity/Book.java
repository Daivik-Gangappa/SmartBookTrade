package com.example.textbookwebapp.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String isbn;
    private String author;
    private String title;
    private String edition;
    private double originalPrice;
    private double currentPrice;
    private double usedTextBookPrice;
    private double renovationPrice;
    private boolean available;
    private int typeId;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Transaction> transactions = new ArrayList<>();

    // Builder pattern for creating Book instances
    public static class BookBuilder {
        private String isbn;
        private String author;
        private String title;
        private String edition;
        private double originalPrice;
        private boolean available;
        private int typeId;

        public BookBuilder setIsbn(String isbn) {
            this.isbn = isbn;
            return this;
        }

        public BookBuilder setAuthor(String author) {
            this.author = author;
            return this;
        }

        public BookBuilder setTitle(String title) {
            this.title = title;
            return this;
        }

        public BookBuilder setEdition(String edition) {
            this.edition = edition;
            return this;
        }

        public BookBuilder setOriginalPrice(double originalPrice) {
            this.originalPrice = originalPrice;
            return this;
        }

        public BookBuilder setTypeId(int typeId) {
            this.typeId = typeId;
            return this;
        }

        public BookBuilder setAvailable(boolean available) {
            this.available = available;
            return this;
        }

        public Book build() {
            Book book = new Book();
            book.isbn = this.isbn;
            book.author = this.author;
            book.title = this.title;
            book.edition = this.edition;
            book.originalPrice = this.originalPrice;
            book.currentPrice = this.originalPrice;
            book.usedTextBookPrice = this.originalPrice; // Set initial used textbook price to original price
            book.available = this.available;
            book.typeId = this.typeId;
            return book;
        }
    }

    // Default constructor required for JPA
    public Book() {}

    // Getters and setters

    public Long getId() {
        return id;
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

    public int getTypeId() {
        return typeId;
    }

    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(List<Transaction> transactions) {
        this.transactions = transactions;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        transaction.setBook(this);
    }
}
