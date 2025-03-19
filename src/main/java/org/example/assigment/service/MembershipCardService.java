package org.example.assigment.service;

import org.example.assigment.model.MembershipCard;
import org.example.assigment.repository.MembershipCardRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MembershipCardService {
    private final MembershipCardRepository membershipCardRepository;

    public MembershipCardService(MembershipCardRepository membershipCardRepository) {
        this.membershipCardRepository = membershipCardRepository;
    }

    // get all
    public List<MembershipCard> getAllMembershipCards() {
        return membershipCardRepository.findAll();
    }

    // find by id
    public MembershipCard getMembershipCardById(Long id) {
        return membershipCardRepository.findById(id).orElse(null);
    }

    // save
    public MembershipCard saveMembershipCard(MembershipCard membershipCard) {
        return membershipCardRepository.save(membershipCard);
    }

    // update
    public MembershipCard updateMembershipCard(Long id, MembershipCard membershipCard) {
        MembershipCard oldMembershipCard = membershipCardRepository.findById(id).orElse(null);
        if (oldMembershipCard != null) {
            oldMembershipCard.setCardNumber(membershipCard.getCardNumber());
            oldMembershipCard.setIssueDate(membershipCard.getIssueDate());
            oldMembershipCard.setExpiryDate(membershipCard.getExpiryDate());
            return membershipCardRepository.save(oldMembershipCard);
        }
        return null;
    }

    // delete
    public void deleteMembershipCard(Long id) {
        membershipCardRepository.deleteById(id);
    }
}
