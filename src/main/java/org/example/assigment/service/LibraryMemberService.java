package org.example.assigment.service;

import org.example.assigment.model.LibraryMember;
import org.example.assigment.repository.LibraryMemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibraryMemberService {
    private final LibraryMemberRepository libraryMemberRepository;

    public LibraryMemberService(LibraryMemberRepository libraryMemberRepository) {
        this.libraryMemberRepository = libraryMemberRepository;
    }

    public List<LibraryMember> getAllLibraryMembers() {
        return libraryMemberRepository.findAll();
    }

    // find by id
    public LibraryMember getLibraryMemberById(Long id) {
        return libraryMemberRepository.findById(id).orElse(null);
    }

    // save
    public LibraryMember saveLibraryMember(LibraryMember libraryMember) {
        return libraryMemberRepository.save(libraryMember);
    }

    // update
    public LibraryMember updateLibraryMember(Long id, LibraryMember libraryMember) {
        LibraryMember oldLibraryMember = libraryMemberRepository.findById(id).orElse(null);
        if (oldLibraryMember != null) {
            oldLibraryMember.setName(libraryMember.getName());
            oldLibraryMember.setEmail(libraryMember.getEmail());
            oldLibraryMember.setMembershipDate(libraryMember.getMembershipDate());
            return libraryMemberRepository.save(oldLibraryMember);
        }
        return null;
    }

    // delete
    public void deleteLibraryMember(Long id) {
        libraryMemberRepository.deleteById(id);
    }

}
