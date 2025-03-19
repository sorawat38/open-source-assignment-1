package org.example.assigment.controller;

import org.example.assigment.model.LibraryMember;
import org.example.assigment.service.LibraryMemberService;
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
    public LibraryMember saveLibraryMember(@RequestBody LibraryMember libraryMember) {
        return libraryMemberService.saveLibraryMember(libraryMember);
    }

    @PutMapping("/{id}")
    public LibraryMember updateLibraryMember(@PathVariable Long id, @RequestBody LibraryMember libraryMember) {
        return libraryMemberService.updateLibraryMember(id, libraryMember);
    }

    @DeleteMapping("/{id}")
    public void deleteLibraryMember(@PathVariable Long id) {
        libraryMemberService.deleteLibraryMember(id);
    }
}

