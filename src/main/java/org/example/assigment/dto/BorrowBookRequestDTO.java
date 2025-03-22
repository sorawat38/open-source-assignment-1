package org.example.assigment.dto;

import jakarta.validation.constraints.NotNull;

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
