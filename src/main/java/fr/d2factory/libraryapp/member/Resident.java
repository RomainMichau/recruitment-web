/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;

/**
 * @author rmichau
 */
public class Resident extends Member {
    final static BigDecimal RESIDENT_NORMAL_PRICE = BigDecimal.valueOf(0.1);
    final static BigDecimal RESIDENT_INCREASED_PRICE = BigDecimal.valueOf(0.20);
    final static int RESIDENT_MAX_TIME_BORROW = 60;

    public Resident(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName, RESIDENT_MAX_TIME_BORROW, RESIDENT_NORMAL_PRICE, RESIDENT_INCREASED_PRICE);
    }

}
