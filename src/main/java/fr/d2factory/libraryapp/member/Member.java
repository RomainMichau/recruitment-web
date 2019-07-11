package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {

    public Member(BigDecimal wallet, String LastName, String FirstName, int maxTimeBorrow, BigDecimal normalPrice, BigDecimal increasedPrice) {
        this.wallet = wallet;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.maxTimeBorrow = maxTimeBorrow;
        this.normalPrice = normalPrice;
        this.increasedPrice = increasedPrice;
    }


    /**
     * An initial sum of money the member has
     */
    protected BigDecimal wallet;
    protected String LastName;
    private String FirstName;

    protected int maxTimeBorrow;
    protected BigDecimal normalPrice;
    protected BigDecimal increasedPrice;

    /**
     * The member should pay their books when they are returned to the library
     *
     * @param numberOfDays the number of days they kept the book
     */
    public void payBook(int numberOfDays) {
        if (numberOfDays <= maxTimeBorrow) {
            //   wallet-=normalPrice*numberOfDays;
            wallet= wallet.subtract(normalPrice.multiply(BigDecimal.valueOf(numberOfDays)));
            return;
        } else if (numberOfDays > maxTimeBorrow) {
            // wallet -= normalPrice * maxTimeBorrow;
            wallet = wallet.subtract(normalPrice.multiply(BigDecimal.valueOf(maxTimeBorrow)));
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

    public BigDecimal getWallet() {
        return wallet;
    }

    public void setWallet(BigDecimal wallet) {
        this.wallet = wallet;
    }

    public int getMaxTimeBorrow() {
        return maxTimeBorrow;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(LastName, member.LastName) &&
                Objects.equals(FirstName, member.FirstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(LastName, FirstName);
    }
}
