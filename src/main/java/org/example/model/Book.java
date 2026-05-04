package org.example.model;

public class Book {
    public String title;
    public String author;
    public String isbn;
    public boolean available;
    public String genre;
    public int year;
    public double price;
    // this field is only used when processing overdue loans, kind of hacky
    public int tempOverdueDays;

    public Book(String title, String author, String isbn, boolean available, String genre, int year, double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.available = available;
        this.genre = genre;
        this.year = year;
        this.price = price;
    }
}