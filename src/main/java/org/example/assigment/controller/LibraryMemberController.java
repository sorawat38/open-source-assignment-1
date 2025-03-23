package org.example.assigment.controller;

import org.example.assigment.dto.BorrowBookRequestForMemberDTO;
import org.example.assigment.dto.BorrowRecordResponseDTO;
import org.example.assigment.dto.MembershipCardResponseDTO;
import org.example.assigment.model.LibraryMember;
import org.example.assigment.service.BorrowRecordService;
import org.example.assigment.service.LibraryMemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/library-members")
public class LibraryMemberController {

    private final LibraryMemberService libraryMemberService;
    private final BorrowRecordService borrowRecordService;

    public LibraryMemberController(LibraryMemberService libraryMemberService, BorrowRecordService borrowRecordService) {
        this.libraryMemberService = libraryMemberService;
        this.borrowRecordService = borrowRecordService;
    }

    // get all library members
    @GetMapping()
    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberService.getAllLibraryMembers();
    }

    // find library member by id
    @GetMapping("/{id}")
    public LibraryMember getLibraryMemberById(@PathVariable Long id) {
        return libraryMemberService.getLibraryMemberById(id);
    }

    // save library member
    @PostMapping()
    public LibraryMember saveLibraryMember(@Validated @RequestBody LibraryMember libraryMember) {
        return libraryMemberService.saveLibraryMember(libraryMember);
    }

    // update library member
    @PutMapping("/{id}")
    public ResponseEntity<?> updateLibraryMember(@PathVariable Long id, @Validated @RequestBody LibraryMember libraryMember) {
        try {
            LibraryMember updatedLibraryMember = libraryMemberService.updateLibraryMember(id, libraryMember);
            return ResponseEntity.ok(updatedLibraryMember);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // delete library member
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibraryMember(@PathVariable Long id) {
        try {
            libraryMemberService.deleteLibraryMember(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Library member deleted successfully.");
    }

    // ================ Membership Card Management ===============
    // assign membership card
    @PostMapping("/{id}/membership-card")
    public ResponseEntity<?> assignMembershipCard(@PathVariable Long id) {
        try {
            MembershipCardResponseDTO membershipCardResponseDTO = libraryMemberService.assignMembershipCard(id);
            return ResponseEntity.status(HttpStatus.CREATED).body(membershipCardResponseDTO);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get membership card by library member id
    @GetMapping("/{id}/membership-card")
    public ResponseEntity<?> getMembershipCard(@PathVariable Long id) {
        try {
            MembershipCardResponseDTO membershipCardResponseDTO = libraryMemberService.getMembershipCardByLibraryMemberId(id);
            return ResponseEntity.ok(membershipCardResponseDTO);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // revoke membership card
    @PostMapping("/{id}/membership-card/revoke")
    public ResponseEntity<?> revokeMembershipCard(@PathVariable Long id) {
        try {
            libraryMemberService.revokeMembershipCard(id);
            return ResponseEntity.ok().body("Membership card revoked successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // get all borrowed books by library member id
    @GetMapping("/{id}/borrowed-books")
    public ResponseEntity<?> getAllBorrowedBooksByLibraryMemberId(@PathVariable Long id) {
        try {
            List<String> borrowedBooks = libraryMemberService.getAllBorrowedBooksByLibraryMemberId(id);
            return ResponseEntity.ok(borrowedBooks);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    // borrow a book
    @PostMapping("/{id}/borrowed-books")
    public ResponseEntity<?> borrowBook(@PathVariable Long id, @Validated @RequestBody BorrowBookRequestForMemberDTO request) {
        try {
            BorrowRecordResponseDTO borrowRecordResponseDTO = borrowRecordService.borrowBook(request.getBookId(), id);
            return ResponseEntity.ok().body(borrowRecordResponseDTO);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

