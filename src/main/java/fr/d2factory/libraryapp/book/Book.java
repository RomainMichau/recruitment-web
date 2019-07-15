package fr.d2factory.libraryapp.book;

import java.util.Objects;

/**
 * A simple representation of a book
 */
public class Book {

    String title;
    String author;
    ISBN isbn;

    /**
     * Default contructor used for deserialization of the JSON by Jackson
     */
    public Book() {
    }

    public Book(String title, String author, ISBN isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public ISBN getIsbn() {
        return isbn;
    }

    /**
     * Check equality of this object with an other
     * @param o other object to compare
     * @return true if the two objects are equals
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return Objects.equals(title, book.title) &&
                Objects.equals(author, book.author) &&
                Objects.equals(isbn, book.isbn);
    }

    /**
     * Generate a hashcode of the  object
     * @return  the hashcode
     */
    @Override
    public int hashCode() {
        return Objects.hash(title, author, isbn);
    }
}
