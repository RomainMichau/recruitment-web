package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.library.exceptions.BookIsNotAvailable;
import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.library.exceptions.HasNotEnoughMoney;
import fr.d2factory.libraryapp.member.Member;
import fr.d2factory.libraryapp.member.MemberBuilder;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.Student;
import fr.d2factory.libraryapp.util.UtilMethod;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class LibraryTest {

    /**
     * Amount of money gived to each member for testing
     */
    private static final BigDecimal INTIAL_MONEY = BigDecimal.valueOf(100);

    /**
     * Path of the JSON file containing books informations
     */
    private static final String BOOKS_FILE = "src/test/resources/books.json";


    private Library library;
    private final BookRepository bookRepository = new BookRepository();
    private Student firstYearStudent;
    private Student seniorStudent;
    private Resident resident;
    List<Book> books;

    /**
     * Initalisation for each test
     */
    @Before
    public void setup() {
        try {
            //Reading book list and instantiate bookRepository
            books = UtilMethod.jsonFileToObject(BOOKS_FILE);
            bookRepository.addBooks(books);
        } catch (IOException ex) {
            Logger.getLogger(LibraryTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Creating thelibrary
        library = new LibraryImpl(bookRepository);
        //Creating tests subjects
        MemberBuilder memberBuilder = new MemberBuilder();
        seniorStudent = memberBuilder.buildMember("Michau", "Romain", INTIAL_MONEY).student(5);
        firstYearStudent = memberBuilder.buildMember("Locqueville", "Fabrice", INTIAL_MONEY).student(1);
        resident = memberBuilder.buildMember("Casara", "Alexandre", INTIAL_MONEY).resident();

    }

    /**
     * Check if a user can effectively borrow an avaiblable bool
     */
    @Test
    public void member_can_borrow_a_book_if_book_is_available() {
        Book book = null;
        final Member member = seniorStudent;
        try {
            book = library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, LocalDate.of(2019, 01, 30));
        } catch (HasLateBooksException e) {
            fail(e.getMessage());
        } catch (BookIsNotAvailable e) {
            fail(e.getMessage());
        }

        assertNotEquals(null, book);
        assertEquals(books.get(0), book);
    }

    /**
     * We check if the return of the book is well taken in account
     */
    @Test
    public void member_can_borrow_a_book_after_given_the_previous_one_back() {
        Book book = null;
        final Member member = seniorStudent;
        try {
            book = library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, LocalDate.of(2019, 01, 30));
            library.returnBook(book, member, LocalDate.of(2019, 02, 25));
            book = library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, LocalDate.of(2019, 05, 30));

        } catch (HasLateBooksException e) {
            fail(e.getMessage());
        } catch (BookIsNotAvailable bookIsNotAvailable) {
            fail(bookIsNotAvailable.getMessage());
        } catch (HasNotEnoughMoney e) {
            fail(e.getMessage());
        }
        assertEquals(books.get(0), book);
    }

    /**
     * Check if a user can't borrow a book if this one is not available
     *
     * @throws BookIsNotAvailable we are expecting to get this exception
     */
    @Test(expected = BookIsNotAvailable.class)
    public void borrowed_book_is_no_longer_available() throws BookIsNotAvailable {

        try {
            library.borrowBook(books.get(0).getIsbn().getIsbnCode(), seniorStudent, LocalDate.of(2019, 01, 30));
            library.borrowBook(books.get(0).getIsbn().getIsbnCode(), firstYearStudent, LocalDate.of(2019, 03, 30));
        } catch (HasLateBooksException e) {
            fail(e.getMessage());
        }
    }


    /**
     * We check that the resident paid to correct amount
     *
     * @throws HasLateBooksException
     * @throws BookIsNotAvailable
     * @throws HasNotEnoughMoney
     */
    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() throws HasLateBooksException, BookIsNotAvailable, HasNotEnoughMoney {
        final Member member = resident;
        final int borrowDuration = 60;
        final double pricePerDay = 0.1;
        final LocalDate borrowDate = LocalDate.of(2019, 01, 1);
        final LocalDate returnDate = borrowDate.plusDays(borrowDuration);
        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, borrowDate);
        library.returnBook(books.get(0), member, returnDate);
        final BigDecimal theoricalPrice = BigDecimal.valueOf(borrowDuration).multiply(BigDecimal.valueOf(pricePerDay));
        assertEquals(theoricalPrice, INTIAL_MONEY.subtract(member.getWallet()));

    }

    /**
     * We check that the student paid the correct amount
     *
     * @throws HasLateBooksException
     * @throws BookIsNotAvailable
     * @throws HasNotEnoughMoney
     */
    @Test
    public void students_pay_10_cents_the_first_30days() throws HasLateBooksException, BookIsNotAvailable, HasNotEnoughMoney {
        final Member member = seniorStudent;
        final int borrowDuration = 30;
        final double pricePerDay = 0.1;

        final LocalDate borrowDate = LocalDate.of(2019, 01, 1);
        final LocalDate returnDate = borrowDate.plusDays(borrowDuration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, borrowDate);
        library.returnBook(books.get(0), member, returnDate);
        final BigDecimal theoricalPrice = BigDecimal.valueOf(borrowDuration).multiply(BigDecimal.valueOf(pricePerDay));
        assertEquals(theoricalPrice, INTIAL_MONEY.subtract(member.getWallet()));
    }

    /**
     * We check that first year student special price is well take in account
     *
     * @throws HasLateBooksException
     * @throws BookIsNotAvailable
     * @throws HasNotEnoughMoney
     */
    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days() throws HasLateBooksException, BookIsNotAvailable, HasNotEnoughMoney {
        final Member member = firstYearStudent;
        final int borrowDuration = 15;
        final double pricePerDay = 0.0;

        final LocalDate borrowDate = LocalDate.of(2019, 01, 1);
        final LocalDate returnDate = borrowDate.plusDays(borrowDuration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, borrowDate);
        library.returnBook(books.get(0), member, returnDate);
        final BigDecimal theoricalPrice = BigDecimal.valueOf(borrowDuration).multiply(BigDecimal.valueOf(pricePerDay));
        final BigDecimal moneyPaidByStudent = INTIAL_MONEY.subtract(member.getWallet());
        assertEquals(moneyPaidByStudent.compareTo(theoricalPrice), 0);
    }

    /**
     * We check that the price is well increased when a student keep a book to long
     *
     * @throws HasLateBooksException
     * @throws BookIsNotAvailable
     * @throws HasNotEnoughMoney
     */
    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() throws HasLateBooksException, BookIsNotAvailable, HasNotEnoughMoney {
        final Member member = seniorStudent;
        final int borrowDuration = 45;
        final int allowedDuration = 30;
        final double normalPrice = 0.1;
        final double increasedPrice = 0.15;

        final LocalDate borrowDate = LocalDate.of(2019, 01, 1);
        final LocalDate returnDate = borrowDate.plusDays(borrowDuration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, borrowDate);
        library.returnBook(books.get(0), member, returnDate);

        final BigDecimal theoricalPriceFFirstPart = BigDecimal.valueOf(allowedDuration).multiply(BigDecimal.valueOf(normalPrice));
        final BigDecimal theoricalPriceSecondPart = BigDecimal.valueOf(borrowDuration - allowedDuration).multiply(BigDecimal.valueOf(increasedPrice));
        final BigDecimal theoricalPrice = theoricalPriceFFirstPart.add(theoricalPriceSecondPart);
        final BigDecimal moneyPaidByStudent = INTIAL_MONEY.subtract(member.getWallet());
        assertEquals(moneyPaidByStudent.compareTo(theoricalPrice), 0);
    }

    /**
     * We check that resident price is well increased if they keep a book more than 60 days;
     *
     * @throws HasLateBooksException
     * @throws BookIsNotAvailable
     * @throws HasNotEnoughMoney
     */
    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() throws HasLateBooksException, BookIsNotAvailable, HasNotEnoughMoney {
        final Member member = resident;
        final int borrowDuration = 100;
        final int allowedDuration = 60;
        final double normalPrice = 0.1;
        final double increasedPrice = 0.2;

        final LocalDate borrowDate = LocalDate.of(2019, 01, 1);
        final LocalDate returnDate = borrowDate.plusDays(borrowDuration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, borrowDate);
        library.returnBook(books.get(0), member, returnDate);

        final BigDecimal theoricalPriceFFirstPart = BigDecimal.valueOf(allowedDuration).multiply(BigDecimal.valueOf(normalPrice));
        final BigDecimal theoricalPriceSecondPart = BigDecimal.valueOf(borrowDuration - allowedDuration).multiply(BigDecimal.valueOf(increasedPrice));
        final BigDecimal theoricalPrice = theoricalPriceFFirstPart.add(theoricalPriceSecondPart);
        final BigDecimal moneyPaidByStudent = INTIAL_MONEY.subtract(member.getWallet());
        assertEquals(moneyPaidByStudent.compareTo(theoricalPrice), 0);
    }

    /**
     * We check that Members cannot boorw a book if they are late
     *
     * @throws HasLateBooksException
     */
    @Test(expected = HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books() throws HasLateBooksException {
        final Member member = seniorStudent;
        try {
            library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, LocalDate.of(2019, 01, 30));
            library.borrowBook(books.get(1).getIsbn().getIsbnCode(), member, LocalDate.of(2019, 03, 30));
        } catch (BookIsNotAvailable e) {
            fail(e.getMessage());
        }
    }


    /**
     * Check that an execption is thrown if the member cannot pay a book
     *
     * @throws HasLateBooksException
     * @throws BookIsNotAvailable
     * @throws HasNotEnoughMoney     we expect this exception
     */
    @Test(expected = HasNotEnoughMoney.class)
    public void members_cant_pay_if_they_have_not_enough_money() throws HasLateBooksException, BookIsNotAvailable, HasNotEnoughMoney {
        final Member member = resident;
        final int borrowDuration = 10000;
        final LocalDate borrowDate = LocalDate.of(2019, 01, 1);
        final LocalDate returnDate = borrowDate.plusDays(borrowDuration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), member, borrowDate);
        library.returnBook(books.get(0), member, returnDate);


    }

}

