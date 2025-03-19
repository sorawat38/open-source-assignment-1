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
        Author oldAuthor = authorRepository.findById(id).orElse(null);
        if (oldAuthor != null) {
            oldAuthor.setName(author.getName());
            oldAuthor.setBiography(author.getBiography());
            return authorRepository.save(oldAuthor);
        }
        return null;
    }

    // delete
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
