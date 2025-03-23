package org.example.assigment.repository;

import org.example.assigment.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("""
                SELECT b FROM Book b
                WHERE b.id NOT IN (
                    SELECT br.book.id FROM BorrowRecord br
                    WHERE br.returnDate IS NULL
                )
            """)
    List<Book> findAvailableBooks();
}
