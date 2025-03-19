-- Insert Library Members
INSERT INTO library_member (id, name, email, membership_date)
VALUES (1, 'Alice Johnson', 'alice@example.com', '2023-01-10'),
       (2, 'Bob Smith', 'bob@example.com', '2023-03-15');

-- Insert Membership Cards
INSERT INTO membership_card (id, card_number, issue_date, expiry_date, library_member_id)
VALUES (1, 'CARD-1234', '2023-01-10', '2026-01-10', 1),
       (2, 'CARD-5678', '2023-03-15', '2026-03-15', 2);

-- Insert Authors
INSERT INTO author (id, name, biography)
VALUES (1, 'J.K. Rowling', 'British author, best known for Harry Potter.'),
       (2, 'George R.R. Martin', 'American novelist and short story writer.'),
       (3, 'Isaac Asimov', 'Science fiction and popular science writer.');

-- Insert Books
INSERT INTO book (id, title, isbn, publication_year)
VALUES (1, 'Harry Potter and the Philosopher''s Stone', '9780747532699', 1997),
       (2, 'A Game of Thrones', '9780553103540', 1996),
       (3, 'Foundation', '9780553293357', 1951);

-- Insert Book-Author Relationships
INSERT INTO book_author (book_id, author_id)
VALUES (1, 1), -- Harry Potter -> J.K. Rowling
       (2, 2), -- Game of Thrones -> George R.R. Martin
       (3, 3);
-- Foundation -> Isaac Asimov

-- Insert Borrow Records
INSERT INTO borrow_record (id, borrow_date, return_date, library_member_id, book_id)
VALUES (1, '2024-01-10', NULL, 1, 1), -- Alice borrowed Harry Potter
       (2, '2024-02-05', '2024-02-20', 2, 2); -- Bob borrowed and returned Game of Thrones