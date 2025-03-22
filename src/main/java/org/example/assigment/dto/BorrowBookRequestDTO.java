package org.example.assigment.dto;

import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) used for receiving a request to borrow a book.
 * This class captures the necessary information: the ID of the library member
 * and the ID of the book to be borrowed. Both fields are required.
 * <p>
 * Used in: POST /api/borrow-records or POST /api/library-members/{id}/borrowed-books
 * <p>
 * Validation is handled using Jakarta Bean Validation annotations.
 */
public class BorrowBookRequestDTO {

    @NotNull(message = "Library member ID is required")
    private Long libraryMemberId;

    @NotNull(message = "Book ID is required")
    private Long bookId;

    public BorrowBookRequestDTO() {
    }

    public Long getLibraryMemberId() {
        return libraryMemberId;
    }

    public void setLibraryMemberId(Long libraryMemberId) {
        this.libraryMemberId = libraryMemberId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
