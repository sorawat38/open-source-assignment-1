package org.example.assigment.dto;

import jakarta.validation.constraints.NotNull;

public class BorrowBookRequestForMemberDTO {
    @NotNull(message = "Book ID is required")
    private Long bookId;

    public BorrowBookRequestForMemberDTO() {
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }
}
