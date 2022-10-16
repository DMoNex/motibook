package com.example.motibook;

public class BookListItem {
    private String author;
    private String bookName;

    public BookListItem() {}
    public BookListItem(String author, String bookName) {
        this.author = author;
        this.bookName = bookName;
    }

    public String getAuthor() { return this.author; }

    public String getBookName() {
        return this.bookName;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

}
