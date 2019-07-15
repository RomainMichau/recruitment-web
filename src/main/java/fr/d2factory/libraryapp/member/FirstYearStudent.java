/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.config.ConfigValue;
import fr.d2factory.libraryapp.library.exceptions.HasNotEnoughMoney;

import java.math.BigDecimal;

/**
 * Represent a student in first year
 * Extends the Class Student
 *
 * @author rmichau
 */
public class FirstYearStudent extends Student {

    /**
     * Represent the number of days during which the Student do not pay for the book
     */
    private static final int FREE_TIME_DURATION = ConfigValue.STUDENT.FIRST_YEAR_STUDENT.FREE_TIME_DURATION_FIRST_YEAR_STUDENT;

    /**
     * The constructor is package private because this object can only be built by the StudentFactory class
     *
     * @param wallet    Money of the student
     * @param LastName  Last of the student
     * @param FirstName First name of the student
     * @see StudentFactory
     */
    FirstYearStudent(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName);
    }

    /**
     * make the member pay for a number of days </br>
     * Due to the particular mechanic of payment for first year Student, this method is Override from Member </br>
     * If the member has not enough money to pay an exception will be thrown, however money will be still take
     * His/Her wallet will be negative then
     *
     * @param numberOfDays the number of days they kept the book
     * @throws HasNotEnoughMoney in case the Student has not enough money to pay
     */
    @Override
    public void payBook(int numberOfDays) throws HasNotEnoughMoney {

        if (numberOfDays <= FREE_TIME_DURATION)
            return;
        else if (numberOfDays <= maxTimeBorrow) {
            wallet = wallet.subtract(
                    normalPrice.multiply(
                            BigDecimal.valueOf(numberOfDays).subtract(
                                    BigDecimal.valueOf(FREE_TIME_DURATION)
                            )
                    )
            );
            if (wallet.compareTo(BigDecimal.ZERO) == -1)
                throw new HasNotEnoughMoney(this);
            return;
        } else if (numberOfDays > maxTimeBorrow) {
            //   wallet -= normalPrice * (maxTimeBorrow - FREE_TIME_DURATION);
            wallet = wallet.subtract(
                    normalPrice.multiply(
                            BigDecimal.valueOf(maxTimeBorrow).subtract(
                                    BigDecimal.valueOf(FREE_TIME_DURATION)
                            )
                    )
            );
            //   wallet -= increasedPrice * (numberOfDays - maxTimeBorrow);
            wallet = wallet.subtract(
                    increasedPrice.multiply(
                            BigDecimal.valueOf(numberOfDays).subtract(
                                    BigDecimal.valueOf(maxTimeBorrow)
                            )
                    )
            );
        }
        return;
    }

}
