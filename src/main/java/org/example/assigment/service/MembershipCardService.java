package org.example.assigment.service;

import org.example.assigment.model.MembershipCard;
import org.example.assigment.repository.LibraryMemberRepository;
import org.example.assigment.repository.MembershipCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipCardService {
    private final MembershipCardRepository membershipCardRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public MembershipCardService(MembershipCardRepository membershipCardRepository, LibraryMemberRepository libraryMemberRepository) {
        this.membershipCardRepository = membershipCardRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    // get all
    public List<MembershipCard> getAllMembershipCards() {
        return membershipCardRepository.findAll();
    }

    // find by id
    public MembershipCard getMembershipCardById(Long id) {
        return membershipCardRepository.findById(id).orElse(null);
    }

    // update
    public MembershipCard updateMembershipCard(Long id, MembershipCard membershipCard) {
        MembershipCard oldMembershipCard = membershipCardRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Membership card with ID " + id + " not found"));

        oldMembershipCard.setCardNumber(membershipCard.getCardNumber());
        oldMembershipCard.setIssueDate(membershipCard.getIssueDate());
        oldMembershipCard.setExpiryDate(membershipCard.getExpiryDate());
        return membershipCardRepository.save(oldMembershipCard);
    }
}
