package org.example.assigment.service;

import org.example.assigment.model.BorrowRecord;
import org.example.assigment.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
    }

    // find by id
    public BorrowRecord getBorrowRecordById(Long id) {
        return borrowRecordRepository.findById(id).orElse(null);
    }

    // save
    public BorrowRecord saveBorrowRecord(BorrowRecord borrowRecord) {
        return borrowRecordRepository.save(borrowRecord);
    }

    // update
    public BorrowRecord updateBorrowRecord(Long id, BorrowRecord borrowRecord) {
        BorrowRecord oldBorrowRecord = borrowRecordRepository.findById(id).orElse(null);
        if (oldBorrowRecord != null) {
            oldBorrowRecord.setBorrowDate(borrowRecord.getBorrowDate());
            oldBorrowRecord.setReturnDate(borrowRecord.getReturnDate());
            return borrowRecordRepository.save(oldBorrowRecord);
        }
        return null;
    }

    // delete
    public void deleteBorrowRecord(Long id) {
        borrowRecordRepository.deleteById(id);
    }
}
