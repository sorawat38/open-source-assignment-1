package org.example.assigment.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.Random;

@Entity
public class MembershipCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Card number is required")
    @Column(nullable = false)
    private String cardNumber;

    @NotNull(message = "Issue date is required")
    @Column(nullable = false)
    private LocalDate issueDate;

    @NotNull(message = "Expiry date is required")
    @Column(nullable = false)
    private LocalDate expiryDate;

    @OneToOne(mappedBy = "membershipCard", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    private LibraryMember libraryMember;

    public MembershipCard() {
        // generate a random 12-character membership card number
        this.cardNumber = generateCardNumber();

        // set the issue date and expiry date
        LocalDate currentDate = LocalDate.now();
        this.issueDate = currentDate;
        this.expiryDate = currentDate.plusYears(3);
    }

    /**
     * Generates a random 12-character membership card number with hyphens.
     * The format is XXXX-XXXX-XXXX, where each X is an uppercase letter (A-Z) or a digit (0-9).
     *
     * @return A randomly generated 12-character alphanumeric string formatted as XXXX-XXXX-XXXX.
     */
    private String generateCardNumber() {
        int length = 12;
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder cardNumber = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < length; i++) {
            if (i > 0 && i % 4 == 0) {
                cardNumber.append('-');
            }
            cardNumber.append(characters.charAt(random.nextInt(characters.length())));
        }
        return cardNumber.toString();
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

    public LocalDate getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(LocalDate issueDate) {
        this.issueDate = issueDate;
    }

    public LocalDate getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDate expiryDate) {
        this.expiryDate = expiryDate;
    }

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    @Override
    public String toString() {
        return "MembershipCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
