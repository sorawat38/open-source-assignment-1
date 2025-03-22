package org.example.assigment.dto;

/**
 * Data Transfer Object (DTO) used for responding with borrow record details.
 * This class provides a simplified view of a borrow record, including the book ID,
 * member ID, borrow date, return date, and record ID.
 * <p>
 * Used in: responses for endpoints like borrowing or viewing borrowed books.
 */
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
