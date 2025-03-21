package org.example.assigment.controller;

import org.example.assigment.model.LibraryMember;
import org.example.assigment.service.LibraryMemberService;
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
    public LibraryMember updateLibraryMember(@PathVariable Long id, @Validated @RequestBody LibraryMember libraryMember) {
        return libraryMemberService.updateLibraryMember(id, libraryMember);
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
}

