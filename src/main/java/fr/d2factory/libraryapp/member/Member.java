package fr.d2factory.libraryapp.member;

import fr.d2factory.libraryapp.library.Library;
import fr.d2factory.libraryapp.library.exceptions.HasNotEnoughMoney;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * A member is a person who can borrow and return books to a {@link Library}
 * A member can be either a student or a resident
 */
public abstract class Member {


    /**
     * Money of the member
     */
    protected BigDecimal wallet;
    protected final String LastName;
    private final String FirstName;

    /**
     * Max time that the member can borrow a book without to be Late
     */
    protected final int maxTimeBorrow;

    /**
     * Normal Price by day
     */
    protected final BigDecimal normalPrice;

    /**
     * Price by day when the book is late
     */
    protected final BigDecimal increasedPrice;

    /**
     * Constructor of Member abstract class
     * This constructor is package private because has has to be call by MemberBuidler (design patter Builder)
     *
     * @param wallet         money of the member
     * @param LastName       Last name of the member
     * @param FirstName      First name of the member
     * @param maxTimeBorrow  Max time that the member can borrow a book without to be Late
     * @param normalPrice    Normal Price by day
     * @param increasedPrice Price by day when the book is late
     * @see MemberBuilder
     */
    Member(BigDecimal wallet, String LastName, String FirstName, int maxTimeBorrow, BigDecimal normalPrice, BigDecimal increasedPrice) {
        this.wallet = wallet;
        this.LastName = LastName;
        this.FirstName = FirstName;
        this.maxTimeBorrow = maxTimeBorrow;
        this.normalPrice = normalPrice;
        this.increasedPrice = increasedPrice;
    }


    /**
     * make the member pay for a number of days </br>
     * If the member has not enough money to pay an exception will be thrown, however money will be still take
     * His/Her wallet will be negative then
     *
     * @param numberOfDays the number of days they kept the book
     * @throws HasNotEnoughMoney in case the member has not enough money to pay
     */
    public void payBook(int numberOfDays) throws HasNotEnoughMoney {
        if (numberOfDays <= maxTimeBorrow) {
            //   wallet-=normalPrice*numberOfDays;
            wallet = wallet.subtract(normalPrice.multiply(BigDecimal.valueOf(numberOfDays)));
            if (wallet.compareTo(BigDecimal.ZERO) == -1)
                throw new HasNotEnoughMoney(this);
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
        if (wallet.compareTo(BigDecimal.ZERO) == -1)
            throw new HasNotEnoughMoney(this);
        return;
    }

    public BigDecimal getWallet() {
        return wallet;
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

    public String getLastName() {
        return LastName;
    }

    public String getFirstName() {
        return FirstName;
    }
}
