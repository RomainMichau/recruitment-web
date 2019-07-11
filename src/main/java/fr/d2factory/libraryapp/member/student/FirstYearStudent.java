/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.d2factory.libraryapp.member.student;

import java.math.BigDecimal;

/**
 * @author rmichau
 */
public class FirstYearStudent extends Student {

    public static final int FREE_TIME_DURATION = 15;

    FirstYearStudent(BigDecimal wallet, String LastName, String FirstName) {
        super(wallet, LastName, FirstName);
    }

    @Override
    public void payBook(int numberOfDays) {
        if (numberOfDays <= FREE_TIME_DURATION)
            return;
        else if (numberOfDays <= maxTimeBorrow) {
            //   wallet -= normalPrice * (numberOfDays-FREE_TIME_DURATION);
            wallet = wallet.subtract(
                    normalPrice.multiply(
                            BigDecimal.valueOf(numberOfDays).subtract(
                                    BigDecimal.valueOf(FREE_TIME_DURATION)
                            )
                    )
            );
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
