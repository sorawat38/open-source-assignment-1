package org.example.assigment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Entity
public class BorrowRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate borrowDate;
    private LocalDate returnDate;

    @NotNull(message = "Library member cannot be null")
    @ManyToOne()
    @JoinColumn(name = "library_member_id", referencedColumnName = "id", nullable = false)
    private LibraryMember libraryMember;

    @NotNull(message = "Book cannot be null")
    @ManyToOne()
    @JoinColumn(name = "book_id", referencedColumnName = "id", nullable = false)
    private Book book;

    public BorrowRecord() {
    }

    public BorrowRecord(LibraryMember libraryMember, Book book) {
        this.libraryMember = libraryMember;
        this.book = book;
        this.borrowDate = LocalDate.now();
        this.returnDate = null; // not returned yet
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
