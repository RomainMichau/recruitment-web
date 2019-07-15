/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.library.exceptions;

import fr.d2factory.libraryapp.book.ISBN;

/**
 * @author rmichau
 * This exception is thrown when a member try to acess to a book which is not avaiblable
 */
public class BookIsNotAvailable extends Exception {

    public BookIsNotAvailable(long isbn, String motif) {
        super("Book " + isbn + " is not available for the following reason: " + motif);
    }
}
