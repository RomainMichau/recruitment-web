/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.config.ConfigValue;

import java.math.BigDecimal;

/**
 * Represent a Resident
 * Extends member
 *
 * @author rmichau
 * @see fr.d2factory.libraryapp.member.Member
 */
public class Resident extends Member {

    /**
     * Package private constructor (only call by MemberFactory)
     * @param wallet wallet of the user
     * @param LastName LastName of the user
     * @param FirstName First Name of the user
     * @see fr.d2factory.libraryapp.member.MemberBuilder.MemberFactory
     */
     Resident(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName, ConfigValue.RESIDENT.MAX_TIME_BORROW, ConfigValue.RESIDENT.NORMAL_PRICE, ConfigValue.RESIDENT.INCREASED_PRICE);
    }

}
