/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.config.ConfigValue;

import java.math.BigDecimal;

/**
 * Abstract class repsenting Student
 * extends Member
 *
 * @author rmichau
 * @see Member
 */
public abstract class Student extends Member {

    /**
     * Constructor of abstract class Student
     *
     * @param wallet    wallet of the student
     * @param LastName  lastname of the student
     * @param FirstName firstname of the student
     */
    public Student(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName, ConfigValue.STUDENT.STUDENT_MAX_TIME_BORROW, ConfigValue.STUDENT.STUDENT_NORMAL_PRICE, ConfigValue.STUDENT.STUDENT_INCREASED_PRICE);
    }


}
