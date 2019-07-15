/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;

/**
 * Represent a Senior Student (not in fist year)
 * extends Student
 *
 * @author rmichau
 * @see Student
 */
public class SeniorStudent extends Student {

    /**
     * Package private (only call by StudentFactory)
     *
     * @param wallet    wallet of the student
     * @param LastName  LastName of the student
     * @param FirstName FirstName of the student
     * @see StudentFactory
     */
    SeniorStudent(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName);
    }

}
