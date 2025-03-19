INSERT INTO library_member (id, name, email, membership_date)
VALUES (1, 'Alice Johnson', 'alice@example.com', '2023-01-10'),
       (2, 'Bob Smith', 'bob@example.com', '2023-03-15');

INSERT INTO membership_card (id, card_number, issue_date, expiry_date, library_member_id)
VALUES (1, 'X8JZ-2M4Q-9WTP', '2023-01-10', '2026-01-10', 1),
       (2, 'L3YV-7A6F-1KBD', '2023-03-15', '2026-03-15', 2);

INSERT INTO author (id, name, biography)
VALUES (1, 'J.K. Rowling', 'British author, best known for Harry Potter.'),
       (2, 'George R.R. Martin', 'American novelist and short story writer.'),
       (3, 'Isaac Asimov', 'Science fiction and popular science writer.');

INSERT INTO book (id, title, isbn, publication_year)
VALUES (1, 'Harry Potter and the Philosopher''s Stone', '9780747532699', 1997),
       (2, 'A Game of Thrones', '9780553103540', 1996),
       (3, 'Foundation', '9780553293357', 1951);

INSERT INTO book_author (book_id, author_id)
VALUES (1, 1),
       (2, 2),
       (3, 3);


INSERT INTO borrow_record (id, borrow_date, return_date, library_member_id, book_id)
VALUES (1, '2024-01-10', NULL, 1, 1),
       (2, '2024-02-05', '2024-02-20', 2, 2);