/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member.student;

import fr.d2factory.libraryapp.member.Member;

import java.math.BigDecimal;

/**
 * @author rmichau
 */
public abstract class Student extends Member {

    final static BigDecimal STUDENT_NORMAL_PRICE = BigDecimal.valueOf(0.1);
    final static BigDecimal STUDENT_INCREASED_PRICE = BigDecimal.valueOf(0.15);
    final static int STUDENT_MAX_TIME_BORROW = 30;

    public Student(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName, STUDENT_MAX_TIME_BORROW, STUDENT_NORMAL_PRICE, STUDENT_INCREASED_PRICE);
    }


}
