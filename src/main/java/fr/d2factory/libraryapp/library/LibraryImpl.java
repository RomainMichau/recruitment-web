package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.library.exceptions.BookIsNotAvailable;
import fr.d2factory.libraryapp.library.exceptions.HasNotEnoughMoney;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

/**
 * Implementation of the Library Interface
 *
 * @see Library
 */
public class LibraryImpl implements Library {

    /**
     * Map with all the members of the library linked to books that they have borrow
     */
    private final Map<Member, Set<Book>> members = new HashMap<>();

    /**
     * Book repository used has a database of books of the library
     *
     * @see BookRepository
     */
    private final BookRepository bookRepository;

    /**
     * Constructor
     *
     * @param bookRepository bookRepository of the libraty
     */
    public LibraryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    /**
     * Register a borrow by a member of the library
     *
     * @param isbnCode   the isbn code of the book
     * @param member     the member who is borrowing the book
     * @param borrowedAt the date when the book was borrowed
     * @return the borrowed book
     * @throws HasLateBooksException in case member as already one or many late book
     */
    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException, BookIsNotAvailable {
        if (checkIfIsLate(member, borrowedAt))
            throw new HasLateBooksException(member);
        Book book = bookRepository.findBook(isbnCode);
        if (book == null)
            throw new BookIsNotAvailable(isbnCode, "book does not exist or has already be borrowed");

        if (!members.containsKey(member))
            members.put(member, new HashSet<>());
        members.get(member).add(book);
        bookRepository.saveBookBorrow(book, borrowedAt);
        return book;
    }

    /**
     * Register the return of the book by a member
     * and make the member paid for the book paid for it
     *
     * @param book       the {@link Book} they return
     * @param member     the {@link Member} who is returning the book
     * @param returnedAt Date of the return
     * @throws HasNotEnoughMoney  if the member is not able to pay for the book
     * @throws BookIsNotAvailable if a member try to return a book that he has not borrowed previously
     */
    @Override
    public void returnBook(Book book, Member member, LocalDate returnedAt) throws HasNotEnoughMoney, BookIsNotAvailable {
        if (!members.get(member).contains(book))
            throw new BookIsNotAvailable(book.getIsbn().getIsbnCode(), "This member has not borrowed this book previously");
        LocalDate borrowedAt = bookRepository.findBorrowedBookDate(book);
        long borrowTime = DAYS.between(borrowedAt, returnedAt);
        member.payBook((int) borrowTime);
        bookRepository.saveBookReturn(book);
        members.get(member).remove(book);
    }


    /**
     * Check if a member has late book
     *
     * @param member    member to check
     * @param verifDate date of the checking
     * @return true if it is the case
     */
    private boolean checkIfIsLate(final Member member, final LocalDate verifDate) {
        if (!members.containsKey(member))
            return false;
        List<LocalDate> dateOfPreviousBorrow = members.get(member).stream().collect(Collectors.mapping(t -> bookRepository.findBorrowedBookDate(t), Collectors.toList()));
        if (dateOfPreviousBorrow.size() == 0)
            return false;
        return dateOfPreviousBorrow.stream().map(t -> DAYS.between(t, verifDate) > member.getMaxTimeBorrow()).filter(t -> t).count() != 0;
    }

}
