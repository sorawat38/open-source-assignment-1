package org.example.assigment.dto;

public class BorrowRecordResponseDTO {
    private Long id;
    private Long bookId;
    private Long libraryMemberId;
    private String borrowDate;
    private String returnDate;

    public BorrowRecordResponseDTO(Long id, Long bookId, Long libraryMemberId, String borrowDate, String returnDate) {
        this.id = id;
        this.bookId = bookId;
        this.libraryMemberId = libraryMemberId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Long getId() {
        return id;
    }

    public Long getBookId() {
        return bookId;
    }

    public Long getLibraryMemberId() {
        return libraryMemberId;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}
