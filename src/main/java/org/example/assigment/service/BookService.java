package org.example.assigment.service;

import org.example.assigment.model.Book;
import org.example.assigment.repository.BookRepository;
import org.example.assigment.repository.BorrowRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BorrowRecordRepository borrowRecordRepository;

    public BookService(BookRepository bookRepository, BorrowRecordRepository borrowRecordRepository) {
        this.bookRepository = bookRepository;
        this.borrowRecordRepository = borrowRecordRepository;
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
        boolean isBorrowed = borrowRecordRepository.existsByBookIdAndReturnDateIsNull(id);
        if (isBorrowed) {
            throw new IllegalStateException("This book is currently borrowed and cannot be deleted.");
        }
        bookRepository.deleteById(id);
    }
}
