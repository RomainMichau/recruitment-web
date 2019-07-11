/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.member.student.Student;
import fr.d2factory.libraryapp.member.student.StudentFactory;

import java.math.BigDecimal;

/**
 *
 * @author rmichau
 */
public class MemberBuilder {

    public MemberBuilder(Library libary) {
        this.libary = libary;
    }
    Library libary;

    public class MemberGetter {

        public MemberGetter(String lastName, String firstName, BigDecimal wallet) {
            this.lastName = lastName;
            this.firstName = firstName;
            this.wallet = wallet;
        }

        String lastName;
        String firstName;
        BigDecimal wallet;
        
        public Student student(int year){
            Student student= StudentFactory.createStudent(wallet, lastName, firstName, year);
            libary.addMember(student);
            return student;
        }
        
        public Resident resident(){
            Resident resident=new Resident(wallet, lastName, firstName);
            libary.addMember(resident);
            return resident;
        }
    }

   public MemberGetter buildMember(String lastName, String firstName, BigDecimal wallet) {
        return new MemberGetter(lastName, firstName, wallet);
    }
}
