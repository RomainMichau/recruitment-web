/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member.student;

import java.math.BigDecimal;

/**
 *
 * @author rmichau
 */
public class StudentFactory {
    public static Student createStudent(BigDecimal wallet, String LastName, String FirstName, int year){
        if(year==1){
            return new FirstYearStudent(wallet, LastName, FirstName);
        }
        else if(year>1){
            return new SeniorStudent(wallet, LastName, FirstName);
        }
        throw new RuntimeException("Year must be more than 0");
    }
}
