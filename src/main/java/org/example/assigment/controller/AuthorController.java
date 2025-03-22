package org.example.assigment.controller;

import org.example.assigment.model.Author;
import org.example.assigment.service.AuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    // get all authors
    @GetMapping()
    public List<Author> getAllAuthors() {
        return authorService.getAllAuthors();
    }

    // find author by id
    @GetMapping("/{id}")
    public Author getAuthorById(@PathVariable Long id) {
        return authorService.getAuthorById(id);
    }

    // save author
    @PostMapping()
    public ResponseEntity<?> saveAuthor(@Validated @RequestBody Author author) {
        Author savedAuthor = authorService.saveAuthor(author);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthor);
    }

    // update author
    @PutMapping("/{id}")
    public ResponseEntity<?> updateAuthor(@PathVariable Long id, @Validated @RequestBody Author author) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, author);
            return ResponseEntity.ok(updatedAuthor);
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }

    // delete author
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.ok("Author deleted successfully.");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
