/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import java.math.BigDecimal;

/**
 * This class is in charge to build some instance which implement the abstact class Member <br/>
 *
 * @author rmichau
 * @see Member
 */
public class MemberBuilder {

    /**
     * Default constructor
     */
    public MemberBuilder() {
    }

    /**
     * Build a MemberFactory with the attribut sent in parameters
     *
     * @param lastName  LastName of the Member
     * @param firstName FirstName of the Member
     * @param wallet    Wallet of the Member
     * @return an instance of the class MemberFactory
     * @see MemberFactory
     */
    public MemberFactory buildMember(String lastName, String firstName, BigDecimal wallet) {
        return new MemberFactory(lastName, firstName, wallet);
    }


    /**
     * This inner class make the user able to choose to create a student or a resident member
     */
    public class MemberFactory {

        /**
         * Constructor of the class MemberFactory
         *
         * @param lastName FirstName of the member
         * @param firstName FirstName of the member
         * @param wallet Wallet of the member
         */
        public MemberFactory(String lastName, String firstName, BigDecimal wallet) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.wallet = wallet;
        }

        private final String lastName;
        private final String firstName;
        private final BigDecimal wallet;

        /**
         * Return an instance of Student which have the attribut sent in the constructor
         * @param year year of the student
         * @return  an instance of Student with the wanted attribut
         */
        public Student student(int year) {
            Student student = StudentFactory.createStudent(wallet, lastName, firstName, year);
            return student;
        }

        /**
         * @return an instance of resident
         */
        public Resident resident() {
            Resident resident = new Resident(wallet, lastName, firstName);
            return resident;
        }
    }
}
