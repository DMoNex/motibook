package com.example.motibook;

public class NoteListItem {
    private String ISBN;
    private String bookName;

    public NoteListItem() {}
    public NoteListItem(String ISBN, String bookName) {
        this.ISBN = ISBN;
        this.bookName = bookName;
    }

    public String getISBN() { return this.ISBN; }

    public String getBookName() {
        return this.bookName;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

}
