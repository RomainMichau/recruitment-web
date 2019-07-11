/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.library.exceptions;

import fr.d2factory.libraryapp.book.ISBN;

/**
 * @author rmichau
 */
public class BookIsNotAvailable extends Exception {


    public BookIsNotAvailable(ISBN isbn, String motif) {
        super("Book " + isbn.getIsbnCode() + " is not available for the following reason: " + motif);
    }

    public BookIsNotAvailable(long isbn, String motif) {
        super("Book " + isbn + " is not available for the following reason: " + motif);
    }
}
