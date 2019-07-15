/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;

/**
 * @author rmichau
 * Factory of the abstract class Student
 * @see Student
 * @see FirstYearStudent
 * @see SeniorStudent
 */
public class StudentFactory {
    /**
     * Decide of the type of student to instantiate (SeniorStudent or FirstYearStudent) with the year sent in parameter
     *
     * @param wallet    Wallet of the student
     * @param LastName  Last name of the student
     * @param FirstName first name of the student
     * @param year      year of studying
     * @return an instance of student
     */

    static Student createStudent(BigDecimal wallet, String LastName, String FirstName, int year) {
        if (year == 1) {
            return new FirstYearStudent(wallet, LastName, FirstName);
        } else if (year > 1) {
            return new SeniorStudent(wallet, LastName, FirstName);
        }
        throw new RuntimeException("Year must be more than 0");
    }
}
