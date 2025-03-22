package org.example.assigment.service;

import org.example.assigment.dto.BorrowBookRequestDTO;
import org.example.assigment.dto.BorrowRecordResponseDTO;
import org.example.assigment.model.Book;
import org.example.assigment.model.BorrowRecord;
import org.example.assigment.model.LibraryMember;
import org.example.assigment.repository.BookRepository;
import org.example.assigment.repository.BorrowRecordRepository;
import org.example.assigment.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BorrowRecordService {
    private final BorrowRecordRepository borrowRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;

    public BorrowRecordService(BorrowRecordRepository borrowRecordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository) {
        this.borrowRecordRepository = borrowRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
    }

    // get all
    public List<BorrowRecord> getAllBorrowRecords() {
        return borrowRecordRepository.findAll();
    }

    // find by id
    public BorrowRecord getBorrowRecordById(Long id) {
        return borrowRecordRepository.findById(id).orElse(null);
    }

    public BorrowRecordResponseDTO borrowBook(BorrowBookRequestDTO borrowBookRequest) {
        Long bookId = borrowBookRequest.getBookId();
        Long memberId = borrowBookRequest.getLibraryMemberId();

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new IllegalArgumentException("Book with ID " + bookId + " not found"));

        LibraryMember member = libraryMemberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Library member with ID " + memberId + " not found"));

        // Check if book is already borrowed
        boolean isBorrowed = borrowRecordRepository.existsByBookIdAndReturnDateIsNull(bookId);
        if (isBorrowed) {
            throw new IllegalStateException("This book is currently borrowed by someone else.");
        }

        // create a new borrow record model
        BorrowRecord record = new BorrowRecord(member, book);
        BorrowRecord saved = borrowRecordRepository.save(record);
        return new BorrowRecordResponseDTO(
                saved.getId(),
                saved.getBook().getId(),
                saved.getLibraryMember().getId(),
                saved.getBorrowDate().toString(),
                saved.getReturnDate().toString()
        );
    }

    public void returnBook(Long id) {
        BorrowRecord borrowRecord = borrowRecordRepository.findById(id).orElse(null);
        if (borrowRecord != null) {
            if (borrowRecord.getReturnDate() != null) {
                throw new IllegalStateException("This book has already been returned.");
            }
            borrowRecord.setReturnDate(java.time.LocalDate.now());
            borrowRecordRepository.save(borrowRecord);
        }
    }
}
