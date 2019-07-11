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
public class SeniorStudent extends Student{
    
    SeniorStudent(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName);
    }
    
}
