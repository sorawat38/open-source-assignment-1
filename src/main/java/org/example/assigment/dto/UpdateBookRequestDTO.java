package org.example.assigment.dto;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;

public class UpdateBookRequestDTO {
    @NotNull(message = "Book title is required")
    private String title;

    @NotNull(message = "ISBN is required")
    private String isbn;

    @Digits(message = "Publication year must be a valid number", integer = 4, fraction = 0)
    @NotNull(message = "Publication year is required")
    private int publicationYear;

    public UpdateBookRequestDTO() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public int getPublicationYear() {
        return publicationYear;
    }

    public void setPublicationYear(int publicationYear) {
        this.publicationYear = publicationYear;
    }
}
