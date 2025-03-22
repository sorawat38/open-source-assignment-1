package org.example.assigment.controller;

import org.example.assigment.model.LibraryMember;
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

    public LibraryMemberController(LibraryMemberService libraryMemberService) {
        this.libraryMemberService = libraryMemberService;
    }

    @GetMapping()
    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberService.getAllLibraryMembers();
    }

    @GetMapping("/{id}")
    public LibraryMember getLibraryMemberById(@PathVariable Long id) {
        return libraryMemberService.getLibraryMemberById(id);
    }

    @PostMapping()
    public LibraryMember saveLibraryMember(@Validated @RequestBody LibraryMember libraryMember) {
        return libraryMemberService.saveLibraryMember(libraryMember);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLibraryMember(@PathVariable Long id) {
        try {
            libraryMemberService.deleteLibraryMember(id);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().body("Library member deleted successfully.");
    }

    @PostMapping("/{id}/membership-card")
    public ResponseEntity<?> assignMembershipCard(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(libraryMemberService.assignMembershipCard(id), HttpStatus.CREATED);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/{id}/membership-card")
    public ResponseEntity<?> getMembershipCard(@PathVariable Long id) {
        try {
            return new ResponseEntity<>(libraryMemberService.getMembershipCardByLibraryMemberId(id), HttpStatus.OK);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/{id}/membership-card/revoke")
    public ResponseEntity<?> revokeMembershipCard(@PathVariable Long id) {
        try {
            libraryMemberService.revokeMembershipCard(id);
            return ResponseEntity.ok().body("Membership card revoked successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}

