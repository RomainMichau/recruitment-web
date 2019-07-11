package fr.d2factory.libraryapp.library;

import fr.d2factory.libraryapp.library.exceptions.HasLateBooksException;
import fr.d2factory.libraryapp.book.Book;
import fr.d2factory.libraryapp.book.BookRepository;
import fr.d2factory.libraryapp.library.exceptions.BookIsNotAvailable;
import fr.d2factory.libraryapp.member.Member;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.temporal.ChronoUnit.DAYS;

public class LibraryImpl implements Library {


    Map<Member, Set<Book>> members = new HashMap<>();
    BookRepository bookRepository;

    public LibraryImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    //static Library Lib


    @Override
    public Book borrowBook(long isbnCode, Member member, LocalDate borrowedAt) throws HasLateBooksException, BookIsNotAvailable {
        if (checkIfIsLate(member, borrowedAt))
            throw new HasLateBooksException();
        Book book = bookRepository.findBook(isbnCode);
        if (book == null)
            throw new BookIsNotAvailable(isbnCode, "book does not exist or has already be borrowed");

        members.get(member).add(book);
        bookRepository.saveBookBorrow(book, borrowedAt);

        return book;
    }

    @Override
    public void returnBook(Book book, Member member,LocalDate returnedAt) {
        LocalDate borrowedAt=bookRepository.findBorrowedBookDate(book);
        long borrowTime=DAYS.between(borrowedAt,returnedAt);
        member.payBook((int)borrowTime);
        bookRepository.saveBookReturn(book);
        members.get(member).remove(book);

    }

    @Override
    public void addMember(Member newMember) {
        members.put(newMember, new HashSet<>());
    }

    public boolean checkIfIsLate(final Member member, final LocalDate verifDate) {
        List<LocalDate> dateOfPreviousBorrow = members.get(member).stream().collect(Collectors.mapping(t -> bookRepository.findBorrowedBookDate(t), Collectors.toList()));
        boolean res = false;
        if (dateOfPreviousBorrow.size() == 0)
            return false;
        return dateOfPreviousBorrow.stream().map(t -> DAYS.between(t, verifDate) > member.getMaxTimeBorrow()).filter(t -> t).count() != 0;
    }
}
