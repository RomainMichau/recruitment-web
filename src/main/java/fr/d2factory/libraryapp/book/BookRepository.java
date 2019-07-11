package fr.d2factory.libraryapp.book;

import fr.d2factory.libraryapp.library.HasLateBooksException;
import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The book repository emulates a database via 2 HashMaps
 */
public class BookRepository  {

    /**
     *  Map of all the available books idenditied by their isbn code
     */
    private Map<ISBN, Book> availableBooks = new HashMap<>();

    /**
     * Map of borrowed books, books identified the borrowing date
     */
    private Map<Book, LocalDate> borrowedBooks = new HashMap<>();

    /**
     * Add a list of books in the map of available books
     * @param books
     */
    public void addBooks(List<Book> books){
        books.forEach(book->availableBooks.put(book.isbn,book));
    }

    /**
     *
     * @param isbnCode
     * @return the book with the corresponding isbn code <br>
     *          null if the book does not exist or is not
     *          available
     */
    public Book findBook(long isbnCode) {
        return availableBooks.get(isbnCode);
    }

    /**
     * Will mark the book as borrowed
     * @param book book to borrow
     * @param borrowedAt date of borrowing
     */
    private void saveBookBorrow(Book book, LocalDate borrowedAt){
        borrowedBooks.put(book,borrowedAt);
        availableBooks.remove(book.isbn);
    }

    /**
     *
     * @param book
     * @return The date when the book in param has been borowed
     *          Will return null il the book is not borrowed or
     *          does not exist
     */
    private LocalDate findBorrowedBookDate(Book book) {
        return borrowedBooks.get(book);
    }
}
