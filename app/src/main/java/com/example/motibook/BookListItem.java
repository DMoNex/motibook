package com.example.motibook;

public class BookListItem {
    private String author;
    private String bookName;
    private String isbn;
    private int kdcCodeLs;

    public BookListItem() {}
    public BookListItem(String author, String bookName, String isbn, int kdcCodeLs) {
        this.author = author;
        this.bookName = bookName;
        this.isbn = isbn;
        this.kdcCodeLs = kdcCodeLs;
    }

    public String getAuthor() { return this.author; }

    public String getBookName() {
        return this.bookName;
    }

    public String getIsbn()  { return this.isbn; }

    public int getKdcCodeLs() { return this.kdcCodeLs; }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public void setIsbn(String isbn) { this.isbn = isbn; }

    public void setKdcCodeLs(int kdcCodeLs) { this.kdcCodeLs = kdcCodeLs; }
}
