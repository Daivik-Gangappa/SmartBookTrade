package com.example.textbookwebapp.entity;

public enum BookType {
    BIOGRAPHY(1),
    TEXTBOOK(2),
    FICTION(3),
    NON_FICTION(4),
    SCIENCE(5),
    HISTORY(6),
    SPORTS(7);

    private final int id;

    BookType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static BookType fromId(int id) {
        for (BookType type : values()) {
            if (type.getId() == id) {
                return type;
            }
        }
        throw new IllegalArgumentException("Invalid typeId: " + id);
    }
}
