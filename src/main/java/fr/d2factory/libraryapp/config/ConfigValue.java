package fr.d2factory.libraryapp.config;

import java.math.BigDecimal;

/**
 * List of all static value of the programm
 */
public class ConfigValue {

    /**
     * Values for member student
     */
    public static class STUDENT {

        public final static BigDecimal STUDENT_NORMAL_PRICE = BigDecimal.valueOf(0.1);
        public final static BigDecimal STUDENT_INCREASED_PRICE = BigDecimal.valueOf(0.15);
        public final static int STUDENT_MAX_TIME_BORROW = 30;

        /**
         * Specific value for first year student
         */
        public static class FIRST_YEAR_STUDENT {
            public static final int FREE_TIME_DURATION_FIRST_YEAR_STUDENT = 15;

        }
    }

    /**
     * Value for resident member
     */
    public static class RESIDENT {
        public static final BigDecimal NORMAL_PRICE = BigDecimal.valueOf(0.1);
        public final static BigDecimal INCREASED_PRICE = BigDecimal.valueOf(0.20);
        public final static int MAX_TIME_BORROW = 60;
    }
}
