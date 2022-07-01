package com.example.myskss;

public class Book {
    private String bookId;
    private String bookTitle;
    private String bookAuthor;
    private String bookPublisher;
    private String bookGenre;
    private int bookPage;
    private int bookISBNNo;
    private int bookAvailability;

    public Book(){
        //this constructor is required
    }

    public Book(String bookId, String bookTitle, String bookAuthor, String bookPublisher, String bookGenre, int bookPage, int bookISBNNo, int bookAvailability) {
        this.bookId = bookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookPublisher = bookPublisher;
        this.bookGenre = bookGenre;
        this.bookPage = bookPage;
        this.bookISBNNo = bookISBNNo;
        this.bookAvailability = bookAvailability;
    }

    public String getBookId() {
        return bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public String getBookPublisher() {
        return bookPublisher;
    }

    public String getBookGenre() {
        return bookGenre;
    }

    public int getBookPage() {
        return bookPage;
    }

    public int getBookISBNNo() {
        return bookISBNNo;
    }

    public int getBookAvailability() {
        return bookAvailability;
    }
}
