package fr.d2factory.libraryapp.library.exceptions;

import fr.d2factory.libraryapp.member.Member;

/**
 * This exception is thrown when a member who owns late books tries to borrow another book
 */
public class HasLateBooksException extends Exception {
    public HasLateBooksException(Member user) {
        super(user.getFirstName()+" "+user.getLastName() +" has one or more books late.");
    }
}
