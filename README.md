# Library Management System – Assignment 1 (CPRG220)

This Spring Boot project implements a RESTful API for a Library Management System. It covers core JPA relationships,
validation, and CRUD functionality for managing library members, books, authors, and borrowing records.

---

## 📚 Features

- Full CRUD support for:
    - `LibraryMember`
    - `MembershipCard`
    - `Book`
    - `Author`
    - `BorrowRecord`
- JPA relationships:
    - One-to-One: `LibraryMember` ↔ `MembershipCard`
    - One-to-Many: `LibraryMember` → `BorrowRecord`
    - Many-to-One: `BorrowRecord` → `Book`
    - Many-to-Many: `Book` ↔ `Author`
- Validation using Jakarta Bean Validation (`@NotNull`, `@NotEmpty`, etc.)
- Business rules:
    - A book cannot be deleted while currently borrowed
    - An author cannot be deleted if assigned to a book
    - A member cannot be deleted if they still have borrowed books
    - A book must have at least one author
- Data seeding using `data.sql`
- JSON-based REST API with `POSTMAN` collection included

