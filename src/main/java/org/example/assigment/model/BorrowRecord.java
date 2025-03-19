package org.example.assigment.model;

import jakarta.persistence.*;

@Entity
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "library_member_id", referencedColumnName = "id")
    private LibraryMember libraryMember;

    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    public BorrowRecord() {
    }

    public BorrowRecord(LibraryMember libraryMember, Book book) {
        this.libraryMember = libraryMember;
        this.book = book;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", libraryMember=" + libraryMember +
                ", book=" + book +
                '}';
    }
}
