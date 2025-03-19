package org.example.assigment.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne()
    @JoinColumn(name = "library_member_id", referencedColumnName = "id", nullable = false)
    private LibraryMember libraryMember;

    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false, unique = true) // set unique to true to ensure a book can only be borrowed by one member at a time
    private Book book;

    @Column(nullable = false)
    private LocalDate borrowDate;
    private LocalDate returnDate;

    public BorrowRecord() {
    }

    public BorrowRecord(LibraryMember libraryMember, Book book, LocalDate borrowDate) {
        this.libraryMember = libraryMember;
        this.book = book;
        this.borrowDate = borrowDate;
        this.returnDate = null; // not returned yet
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

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(LocalDate borrowDate) {
        this.borrowDate = borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    @Override
    public String toString() {
        return "BorrowRecord{" +
                "id=" + id +
                ", libraryMember=" + libraryMember +
                ", book=" + book +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                '}';
    }
}
