package org.example.assigment.repository;

import org.example.assigment.model.BorrowRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BorrowRecordRepository extends JpaRepository<BorrowRecord, Long> {
    boolean existsByBookIdAndReturnDateIsNull(Long bookId);
}
