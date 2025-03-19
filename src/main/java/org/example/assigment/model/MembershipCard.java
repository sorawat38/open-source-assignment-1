package org.example.assigment.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.Random;

@Entity
public class MembershipCard {
    @Id
    private String cardNumber;
    private String issueDate;
    private String expiryDate;

    @OneToOne(mappedBy = "membershipCard", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH})
    @JsonBackReference
    private LibraryMember libraryMember;

    public MembershipCard() {
    }

    public MembershipCard(String issueDate, String expiryDate, LibraryMember libraryMember) {
        this.cardNumber = generateCardNumber();
        this.issueDate = issueDate;
        this.expiryDate = expiryDate;
        this.libraryMember = libraryMember;
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

    public LibraryMember getLibraryMember() {
        return libraryMember;
    }

    public void setLibraryMember(LibraryMember libraryMember) {
        this.libraryMember = libraryMember;
    }

    @Override
    public String toString() {
        return "MembershipCard{" +
                "cardNumber='" + cardNumber + '\'' +
                ", issueDate='" + issueDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                '}';
    }
}
