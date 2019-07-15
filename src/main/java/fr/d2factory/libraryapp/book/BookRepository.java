package fr.d2factory.libraryapp.book;

import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository {

    /**
     * Map of all the available books identified by their isbn code
     */
    private final Map<ISBN, Book> availableBooks = new HashMap<>();

    /**
     * Map of borrowed books, books identified the borrowing date
     */
    private final Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    /**
     * Add a list of books in the map of available books
     *
     * @param books List of books to add
     */
    public void addBooks(List<Book> books) {
        books.forEach(book -> availableBooks.put(book.isbn, book));
    }

    /**
     * @param isbnCode ISBN code of the book to find
     * @return the book with the corresponding isbn code <br/>
     * null if the book does not exist or is not
     * available
     */
    public Book findBook(long isbnCode) {
        return availableBooks.get(new ISBN(isbnCode));
    }

    /**
     * Will mark the book as borrowed
     *
     * @param book       book to borrow
     * @param borrowedAt date of borrowing
     */
    public void saveBookBorrow(Book book, LocalDate borrowedAt) {
        borrowedBooks.put(availableBooks.remove(book.isbn), borrowedAt);

    }

    /**
     * Will mark the book has available
     *
     * @param book boot to mark
     */
    public void saveBookReturn(Book book) {
        availableBooks.put(book.isbn, book);
        borrowedBooks.remove(book);
    }

    /**
     * @param book
     * @return The date when the book in param has been borrowed
     * Will return null il the book is not borrowed or
     * does not exist
     */
    public LocalDate findBorrowedBookDate(Book book) {
        return borrowedBooks.get(book);
    }
}
