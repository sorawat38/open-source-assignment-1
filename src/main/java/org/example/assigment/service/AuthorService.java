package org.example.assigment.service;

import org.example.assigment.model.Author;
import org.example.assigment.repository.AuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    // get all
    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    // find by id
    public Author getAuthorById(Long id) {
        return authorRepository.findById(id).orElse(null);
    }

    // save
    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }

    // update
    public Author updateAuthor(Long id, Author author) {

        Author oldAuthor = authorRepository.findById(id).orElseThrow(() ->
                new IllegalStateException("Can't find the author by id"));
        
        oldAuthor.setName(author.getName());
        oldAuthor.setBiography(author.getBiography());
        return authorRepository.save(oldAuthor);
    }

    // delete
    public void deleteAuthor(Long id) {
        // find the author that don't have book record
        Author author = authorRepository.findById(id).orElseThrow(
                () -> new IllegalStateException("Can't find the author by id"));

        if (!author.getBooks().isEmpty()) {
            throw new IllegalStateException("Can't delete linked author with book");
        }

        authorRepository.deleteById(id);
    }
}
