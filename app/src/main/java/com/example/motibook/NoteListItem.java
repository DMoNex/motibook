package com.example.motibook;

public class NoteListItem {
    private String ISBN;
    private String bookName;
    private int rating;

    public NoteListItem() {}
    public NoteListItem(String ISBN, String bookName, int rating) {
        this.ISBN = ISBN;
        this.bookName = bookName;
        this.rating = rating;
    }

    public String getISBN() { return this.ISBN; }

    public String getBookName() {
        return this.bookName;
    }

    public int getRating() { return this.rating; }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setRating(int rating) { this.rating = rating; }
}
