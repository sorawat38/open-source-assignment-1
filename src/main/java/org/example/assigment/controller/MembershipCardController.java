package org.example.assigment.controller;

import org.example.assigment.model.MembershipCard;
import org.example.assigment.service.MembershipCardService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/membership-cards")
public class MembershipCardController {
    private final MembershipCardService membershipCardService;

    public MembershipCardController(MembershipCardService membershipCardService) {
        this.membershipCardService = membershipCardService;
    }

    // get all membership cards
    @GetMapping()
    public List<MembershipCard> getAllMembershipCards() {
        return membershipCardService.getAllMembershipCards();
    }

    // find membership card by id
    @GetMapping("/{id}")
    public MembershipCard getMembershipCardById(@PathVariable Long id) {
        return membershipCardService.getMembershipCardById(id);
    }

    // create a new membership card
    @PutMapping("/{id}")
    public MembershipCard updateMembershipCard(@PathVariable Long id, @Validated @RequestBody MembershipCard membershipCard) {
        return membershipCardService.updateMembershipCard(id, membershipCard);
    }
}
