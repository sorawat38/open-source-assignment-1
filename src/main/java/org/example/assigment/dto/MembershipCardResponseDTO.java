package org.example.assigment.dto;

/**
 * Data Transfer Object (DTO) used for responding with membership card details.
 * Contains relevant information such as card number, issue and expiry dates,
 * and the associated library member ID.
 * <p>
 * Used in: responses for endpoints related to membership card assignment and retrieval.
 */
public class MembershipCardResponseDTO {
    private Long id;
    private String cardNumber;
    private String issueDate;
    private String expiryDate;
    private Long libraryMemberId;

    public MembershipCardResponseDTO(Long id, String cardNumber, String issueDate, String expiryDate, Long libraryMemberId) {
        this.id = id;
        this.cardNumber = cardNumber;
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.libraryMemberId = libraryMemberId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public String getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Long getLibraryMemberId() {
        return libraryMemberId;
    }

    public void setLibraryMemberId(Long libraryMemberId) {
        this.libraryMemberId = libraryMemberId;
    }

    @Override
    public String toString() {
        return "MembershipCardResponseDTO{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", libraryMemberId=" + libraryMemberId +
                '}';
    }
}
