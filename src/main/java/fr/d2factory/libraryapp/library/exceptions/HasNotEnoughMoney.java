/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.library.exceptions;


import fr.d2factory.libraryapp.member.Member;

/**
 * @author rmichau
 * This exception is thrown when a member has to pay for a book,but has not enough money
 */
public class HasNotEnoughMoney extends Exception {
    public HasNotEnoughMoney(Member member) {
        super(member.getFirstName() + " " + member.getLastName() + " cannot pay for the book.");
    }
}



