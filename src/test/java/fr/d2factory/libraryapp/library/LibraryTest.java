package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.library.exceptions.BookIsNotAvailable;
import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.member.MemberBuilder;
import fr.d2factory.libraryapp.member.Resident;
import fr.d2factory.libraryapp.member.student.Student;
import fr.d2factory.libraryapp.util.UtilMethod;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.Test;
;

import static java.time.temporal.ChronoUnit.DAYS;
import static org.junit.Assert.*;

public class LibraryTest {

    private static final BigDecimal INTIAL_MONEY=BigDecimal.valueOf(100);
    private static final String BOOKS_FILE = "src/test/resources/books.json";
    private Library library;
    private BookRepository bookRepository = new BookRepository();
    private Student fabriceF;
    private Student romainM;
    private Resident alexandreC;
    List<Book> books;

    @Before
    public void setup() {

        try {
            books = UtilMethod.jsonFileToObject(BOOKS_FILE);
            bookRepository.addBooks(books);
        } catch (IOException ex) {
            Logger.getLogger(LibraryTest.class.getName()).log(Level.SEVERE, null, ex);
        }

        library = new LibraryImpl(bookRepository);
        MemberBuilder memberBuilder = new MemberBuilder(library);
        romainM = memberBuilder.buildMember("Michau", "Romain", INTIAL_MONEY).student(5);
        fabriceF = memberBuilder.buildMember("Locqueville", "Fabrice", INTIAL_MONEY).student(1);
        alexandreC = memberBuilder.buildMember("Casara", "Alexandre", INTIAL_MONEY).resident();

    }

    @Test
    public void member_can_borrow_a_book_if_book_is_available() {
        Book book = null;

        try {
            book = library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, LocalDate.of(2019, 01, 30));
        } catch (HasLateBooksException e) {
            fail(e.getMessage());
        } catch (BookIsNotAvailable bookIsNotAvailable) {
            fail(bookIsNotAvailable.getMessage());
        }

        assertNotEquals(null, book);
        assertEquals(books.get(0), book);

    }

    @Test
    public void member_can_borrow_a_book_after_given_the_previous_one_back() {
        Book book = null;

        try {
            book = library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, LocalDate.of(2019, 01, 30));
            library.returnBook(book,romainM,LocalDate.of(2019, 02, 25));
            book = library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, LocalDate.of(2019, 05, 30));

        } catch (HasLateBooksException e) {
            fail(e.getMessage());
        } catch (BookIsNotAvailable bookIsNotAvailable) {
            fail(bookIsNotAvailable.getMessage());
        }
        assertEquals(books.get(0), book);   
    }

    @Test(expected = BookIsNotAvailable.class)
    public void borrowed_book_is_no_longer_available() throws BookIsNotAvailable {

        try {
            library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, LocalDate.of(2019, 01, 30));
            library.borrowBook(books.get(0).getIsbn().getIsbnCode(), fabriceF, LocalDate.of(2019, 03, 30));
        } catch (HasLateBooksException e) {
            fail(e.getMessage());
        }
    }


    @Test
    public void residents_are_taxed_10cents_for_each_day_they_keep_a_book() throws HasLateBooksException, BookIsNotAvailable {
        int duration=60;
        double pricePerDay=0.1;
        LocalDate date1=LocalDate.of(2019, 01, 1);
        LocalDate date2=date1.plusDays(duration);
        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), alexandreC, date1);
        library.returnBook(books.get(0), alexandreC, date2);
        BigDecimal theoricalPrice=BigDecimal.valueOf(duration).multiply(BigDecimal.valueOf(pricePerDay)) ;
        assertEquals(theoricalPrice,INTIAL_MONEY.subtract(alexandreC.getWallet()));

    }

    @Test
    public void students_pay_10_cents_the_first_30days() throws HasLateBooksException, BookIsNotAvailable {
        int duration=30;
        double pricePerDay=0.1;
        LocalDate date1=LocalDate.of(2019, 01, 1);
        LocalDate date2=date1.plusDays(duration);
        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, date1);
        library.returnBook(books.get(0), romainM, date2);
        BigDecimal theoricalPrice=BigDecimal.valueOf(duration).multiply(BigDecimal.valueOf(pricePerDay)) ;
        assertEquals(theoricalPrice,INTIAL_MONEY.subtract(romainM.getWallet()));
    }

    @Test
    public void students_in_1st_year_are_not_taxed_for_the_first_15days() throws HasLateBooksException, BookIsNotAvailable {
        int duration=15;
        double pricePerDay=0.0;
        LocalDate date1=LocalDate.of(2019, 01, 1);
        LocalDate date2=date1.plusDays(duration);
        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), fabriceF, date1);
        library.returnBook(books.get(0), fabriceF, date2);
        BigDecimal theoricalPrice=BigDecimal.valueOf(duration).multiply(BigDecimal.valueOf(pricePerDay)) ;
        BigDecimal moneyPaidByStudent=INTIAL_MONEY.subtract(fabriceF.getWallet());
        assertEquals(moneyPaidByStudent.compareTo(theoricalPrice),0);
    }

    @Test
    public void students_pay_15cents_for_each_day_they_keep_a_book_after_the_initial_30days() throws HasLateBooksException, BookIsNotAvailable {
        int duration=45;
        int firstPartDuration=30;
        double firstPartPrice=0.1;
        double secondPartPrice=0.15;

        LocalDate date1=LocalDate.of(2019, 01, 1);
        LocalDate date2=date1.plusDays(duration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, date1);
        library.returnBook(books.get(0), romainM, date2);

        BigDecimal theoricalPriceFFirstPart=BigDecimal.valueOf(firstPartDuration).multiply(BigDecimal.valueOf(firstPartPrice)) ;
        BigDecimal theoricalPriceSecondPart=BigDecimal.valueOf(duration-firstPartDuration).multiply(BigDecimal.valueOf(secondPartPrice)) ;
        BigDecimal theoricalPrice=theoricalPriceFFirstPart.add(theoricalPriceSecondPart);
        BigDecimal moneyPaidByStudent=INTIAL_MONEY.subtract(romainM.getWallet());
        assertEquals(moneyPaidByStudent.compareTo(theoricalPrice),0);
    }

    @Test
    public void residents_pay_20cents_for_each_day_they_keep_a_book_after_the_initial_60days() throws HasLateBooksException, BookIsNotAvailable {
        int duration=100;
        int firstPartDuration=60;
        double firstPartPrice=0.1;
        double secondPartPrice=0.2;

        LocalDate date1=LocalDate.of(2019, 01, 1);
        LocalDate date2=date1.plusDays(duration);

        library.borrowBook(books.get(0).getIsbn().getIsbnCode(), alexandreC, date1);
        library.returnBook(books.get(0), alexandreC, date2);

        BigDecimal theoricalPriceFFirstPart=BigDecimal.valueOf(firstPartDuration).multiply(BigDecimal.valueOf(firstPartPrice)) ;
        BigDecimal theoricalPriceSecondPart=BigDecimal.valueOf(duration-firstPartDuration).multiply(BigDecimal.valueOf(secondPartPrice)) ;
        BigDecimal theoricalPrice=theoricalPriceFFirstPart.add(theoricalPriceSecondPart);
        BigDecimal moneyPaidByStudent=INTIAL_MONEY.subtract(alexandreC.getWallet());
        assertEquals(moneyPaidByStudent.compareTo(theoricalPrice),0);
    }

    @Test(expected = HasLateBooksException.class)
    public void members_cannot_borrow_book_if_they_have_late_books() throws HasLateBooksException {
        try {
            library.borrowBook(books.get(0).getIsbn().getIsbnCode(), romainM, LocalDate.of(2019, 01, 30));
            library.borrowBook(books.get(1).getIsbn().getIsbnCode(), romainM, LocalDate.of(2019, 03, 30));
        } catch (BookIsNotAvailable e) {
            fail(e.getMessage());
        }
    }


}

