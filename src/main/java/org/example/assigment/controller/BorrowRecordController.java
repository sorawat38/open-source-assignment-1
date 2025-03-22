package org.example.assigment.controller;

import org.example.assigment.dto.BorrowBookRequestDTO;
import org.example.assigment.dto.BorrowRecordResponseDTO;
import org.example.assigment.model.BorrowRecord;
import org.example.assigment.service.BorrowRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> borrowBook(@Validated @RequestBody BorrowBookRequestDTO request) {
        try {
            BorrowRecordResponseDTO response = borrowRecordService.borrowBook(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    @PatchMapping("/{id}/return")
    public ResponseEntity<?> returnBook(@PathVariable Long id) {
        try {
            borrowRecordService.returnBook(id);
            return ResponseEntity.ok("Book returned successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
