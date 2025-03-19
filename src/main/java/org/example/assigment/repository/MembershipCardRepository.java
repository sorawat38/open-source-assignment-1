package org.example.assigment.repository;

import org.example.assigment.model.MembershipCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipCardRepository extends JpaRepository<MembershipCard, String> {
}
