package org.example.assigment.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class LibraryMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private LocalDate membershipDate;

    @OneToOne(cascade = CascadeType.ALL) // If a member is deleted, their membership card should also be deleted
    @JoinColumn(name = "membership_card_id", referencedColumnName = "id")
    private MembershipCard membershipCard;

    @OneToMany(mappedBy = "libraryMember", cascade = CascadeType.ALL)
    // If a member is deleted, delete all borrow records
    private List<BorrowRecord> borrowRecords;

    public LibraryMember() {
    }

    public LibraryMember(String name, String email, LocalDate membershipDate) {
        this.name = name;
        this.email = email;
        this.membershipDate = membershipDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getMembershipDate() {
        return membershipDate;
    }

    public void setMembershipDate(LocalDate membershipDate) {
        this.membershipDate = membershipDate;
    }

    public MembershipCard getMembershipCard() {
        return membershipCard;
    }

    public void setMembershipCard(MembershipCard membershipCard) {
        this.membershipCard = membershipCard;
    }

    public List<BorrowRecord> getBorrowRecords() {
        return borrowRecords;
    }

    public void setBorrowRecords(List<BorrowRecord> borrowRecords) {
        this.borrowRecords = borrowRecords;
    }

    @Override
    public String toString() {
        return "LibraryMember{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", membershipDate=" + membershipDate +
                ", membershipCard=" + membershipCard +
                '}';
    }
}
