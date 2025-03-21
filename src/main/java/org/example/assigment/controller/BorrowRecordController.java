package org.example.assigment.controller;

import org.example.assigment.model.BorrowRecord;
import org.example.assigment.service.BorrowRecordService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/borrow-records")
public class BorrowRecordController {
    private final BorrowRecordService borrowRecordService;

    public BorrowRecordController(BorrowRecordService borrowRecordService) {
        this.borrowRecordService = borrowRecordService;
    }

    @GetMapping()
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordService.getAllBorrowRecords();
    }

    @GetMapping("/{id}")
    public BorrowRecord getBorrowRecordById(@PathVariable Long id) {
        return borrowRecordService.getBorrowRecordById(id);
    }

    @PostMapping()
    public BorrowRecord saveBorrowRecord(@Validated @RequestBody BorrowRecord borrowRecord) {
        return borrowRecordService.saveBorrowRecord(borrowRecord);
    }

    @PutMapping("/{id}")
    public BorrowRecord updateBorrowRecord(@PathVariable Long id, @Validated @RequestBody BorrowRecord borrowRecord) {
        return borrowRecordService.updateBorrowRecord(id, borrowRecord);
    }

    @DeleteMapping("/{id}")
    public void deleteBorrowRecord(@PathVariable Long id) {
        borrowRecordService.deleteBorrowRecord(id);
    }
}
