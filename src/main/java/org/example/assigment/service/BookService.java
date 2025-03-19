package org.example.assigment.service;

import org.example.assigment.model.Author;
import org.example.assigment.model.Book;
import org.example.assigment.repository.AuthorRepository;
import org.example.assigment.repository.BookRepository;
import org.example.assigment.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, BorrowRecordRepository borrowRecordRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
        this.authorRepository = authorRepository;
    }

    // get all
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // find by id
    public Book getBookById(Long id) {
        return bookRepository.findById(id).orElse(null);
    }

    // save
    public Book saveBook(Book book) {
        // validate that the book has at least one author
        if (book.getAuthors() == null || book.getAuthors().isEmpty()) {
            throw new IllegalArgumentException("A book must have at least one author.");
        }

        // ensure authors exist before assigning them to the book
        Set<Author> managedAuthors = new HashSet<>();
        for (Author author : book.getAuthors()) {
            Author managedAuthor = authorRepository.findById(author.getId())
                    .orElseThrow(() -> new RuntimeException("Author not found with ID: " + author.getId()));
            managedAuthors.add(managedAuthor);
        }

        book.setAuthors(managedAuthors);

        return bookRepository.save(book);
    }

    // update
    public Book updateBook(Long id, Book book) {
        Book oldBook = bookRepository.findById(id).orElse(null);
        if (oldBook != null) {
            oldBook.setTitle(book.getTitle());
            oldBook.setIsbn(book.getIsbn());
            oldBook.setPublicationYear(book.getPublicationYear());
            return bookRepository.save(oldBook);
        }
        return null;
    }

    // delete
    public void deleteBook(Long id) {
        boolean isBorrowed = borrowRecordRepository.existsByBookIdAndReturnDateIsNull(id);
        if (isBorrowed) {
            throw new IllegalStateException("This book is currently borrowed and cannot be deleted.");
        }
        bookRepository.deleteById(id);
    }
}
