package org.example.assigment.service;

import org.example.assigment.dto.MembershipCardResponseDTO;
import org.example.assigment.model.BorrowRecord;
import org.example.assigment.model.LibraryMember;
import org.example.assigment.model.MembershipCard;
import org.example.assigment.repository.BorrowRecordRepository;
import org.example.assigment.repository.LibraryMemberRepository;
import org.example.assigment.repository.MembershipCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryMemberService {
    private final LibraryMemberRepository libraryMemberRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final MembershipCardRepository membershipCardRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository, BorrowRecordRepository borrowRecordRepository, MembershipCardRepository membershipCardRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.membershipCardRepository = membershipCardRepository;
    }

    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberRepository.findAll();
    }

    // find by id
    public LibraryMember getLibraryMemberById(Long id) {
        return libraryMemberRepository.findById(id).orElse(null);
    }

    // save
    public LibraryMember saveLibraryMember(LibraryMember libraryMember) {
        return libraryMemberRepository.save(libraryMember);
    }

    // update
    public LibraryMember updateLibraryMember(Long id, LibraryMember libraryMember) {
        LibraryMember oldLibraryMember = libraryMemberRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Library member not found"));
        
        oldLibraryMember.setName(libraryMember.getName());
        oldLibraryMember.setEmail(libraryMember.getEmail());
        oldLibraryMember.setMembershipDate(libraryMember.getMembershipDate());
        return libraryMemberRepository.save(oldLibraryMember);
    }

    // delete
    public void deleteLibraryMember(Long id) {
        // check that return all books then arrow to delete
        List<BorrowRecord> borrowRecord = borrowRecordRepository.findByLibraryMemberId(id);
        if (borrowRecord != null) {
            throw new RuntimeException("Library member has not returned all books");
        }

        libraryMemberRepository.deleteById(id);
    }

    public MembershipCardResponseDTO assignMembershipCard(Long id) {
        // check that library member exists
        LibraryMember libraryMember = libraryMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Library member not found"));

        // check that library member has not already been assigned a membership card
        if (libraryMember.getMembershipCard() != null) {
            throw new IllegalStateException("Library member already has a membership card");
        }

        MembershipCard card = new MembershipCard();

        card.setLibraryMember(libraryMember);
        libraryMember.setMembershipCard(card);

        MembershipCard savedCard = membershipCardRepository.save(card);

        return new MembershipCardResponseDTO(
                savedCard.getId(),
                savedCard.getCardNumber(),
                savedCard.getIssueDate().toString(),
                savedCard.getExpiryDate().toString(),
                savedCard.getLibraryMember().getId()
        );
    }

    public MembershipCardResponseDTO getMembershipCardByLibraryMemberId(Long id) {
        LibraryMember libraryMember = libraryMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Library member not found"));

        MembershipCard card = libraryMember.getMembershipCard();
        if (card == null) {
            throw new IllegalStateException("Library member does not have a membership card");
        }

        return new MembershipCardResponseDTO(
                card.getId(),
                card.getCardNumber(),
                card.getIssueDate().toString(),
                card.getExpiryDate().toString(),
                card.getLibraryMember().getId()
        );
    }

    public void revokeMembershipCard(Long id) {
        LibraryMember libraryMember = libraryMemberRepository.findById(id)
                .orElseThrow(() -> new IllegalStateException("Library member not found"));

        MembershipCard card = libraryMember.getMembershipCard();
        if (card == null) {
            throw new IllegalStateException("Library member does not have a membership card");
        }

        libraryMember.setMembershipCard(null); // detach the card from the library member
        membershipCardRepository.delete(card);
        libraryMemberRepository.save(libraryMember);
    }
}
