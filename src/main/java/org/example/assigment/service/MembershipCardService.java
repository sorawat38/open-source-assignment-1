package org.example.assigment.service;

import org.example.assigment.model.MembershipCard;
import org.example.assigment.repository.MembershipCardRepository;
import org.springframework.stereotype.Service;

@Service
public class MembershipCardService {
    private final MembershipCardRepository membershipCardRepository;

    public MembershipCardService(MembershipCardRepository membershipCardRepository) {
        this.membershipCardRepository = membershipCardRepository;
    }

    // find by id
    public MembershipCard getMembershipCardById(String cardNumber) {
        return membershipCardRepository.findById(cardNumber).orElse(null);
    }

    // save
    public MembershipCard saveMembershipCard(MembershipCard membershipCard) {
        return membershipCardRepository.save(membershipCard);
    }

    // update
    public MembershipCard updateMembershipCard(String cardNumber, MembershipCard membershipCard) {
        MembershipCard oldMembershipCard = membershipCardRepository.findById(cardNumber).orElse(null);
        if (oldMembershipCard != null) {
            oldMembershipCard.setLibraryMember(membershipCard.getLibraryMember());
            oldMembershipCard.setIssueDate(membershipCard.getIssueDate());
            oldMembershipCard.setExpiryDate(membershipCard.getExpiryDate());
            return membershipCardRepository.save(oldMembershipCard);
        }
        return null;
    }

    // delete
    public void deleteMembershipCard(String cardNumber) {
        membershipCardRepository.deleteById(cardNumber);
    }
}
