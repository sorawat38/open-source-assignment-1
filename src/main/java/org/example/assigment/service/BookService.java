package org.example.assigment.service;

import org.example.assigment.model.Book;
import org.example.assigment.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
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
        bookRepository.deleteById(id);
    }
}
