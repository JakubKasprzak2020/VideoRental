package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateCostOfCopiesInCartTest {

    @Autowired
    private CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;
    @Autowired
    private Cart cart;
    @Autowired
    private EmptyACart emptyACart;

    private static final User regularUser = User.builder().name("RegularUser").userType(UserType.REGULAR).build();
    private static final User silverUser = User.builder().name("SilverUser").userType(UserType.SILVER).build();
    private static final User goldUser = User.builder().name("GoldUser").userType(UserType.GOLD).build();
    private static final User platinumUser = User.builder().name("PlatinumUser").userType(UserType.PLATINUM).build();

    private static final Movie premiereMovie = Movie.builder().title("Premiere Movie").releaseDate(LocalDate.of(2020, 12, 1)).build();
    private static final Movie newMovie = Movie.builder().title("New Movie").releaseDate(LocalDate.of(2020, 10, 1)).build();
    private static final Movie standardMovie = Movie.builder().title("Standard Movie").releaseDate(LocalDate.of(2020, 5, 1)).build();
    private static final Movie classicMovie = Movie.builder().title("Classic Movie").releaseDate(LocalDate.of(2005, 12, 1)).build();

    private static final Copy premiereCopy = Copy.builder().movie(premiereMovie).build();
    private static final Copy newCopy = Copy.builder().movie(newMovie).build();
    private static final Copy standardCopy = Copy.builder().movie(standardMovie).build();
    private static final Copy classicCopy = Copy.builder().movie(classicMovie).build();

    private static final LocalDate RENTAL_DATE = LocalDate.of(2020, 12, 2);


    @ParameterizedTest
    @MethodSource("providesCopiesWithImpactOfDateRelease")
    void shouldGiveImpactOfDatesReleases(Copy copy, BigDecimal expected) {
        //when
        BigDecimal result = calculateCostOfCopiesInCart.getImpactOfDateRelease(copy, RENTAL_DATE);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> providesCopiesWithImpactOfDateRelease() {
        return Stream.of(
                Arguments.of(premiereCopy, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_PREMIERE_MOVIE),
                Arguments.of(newCopy, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_NEW_MOVIE),
                Arguments.of(standardCopy, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_STANDARD_MOVIE),
                Arguments.of(classicCopy, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_CLASSIC_MOVIE)
        );
    }


    @ParameterizedTest
    @MethodSource("providesUserWithImpactOfUserType")
    void shouldGiveImpactOfUsersTypes(User user, BigDecimal expected) {
        //when
        BigDecimal result = calculateCostOfCopiesInCart.getImpactOfUserType(user);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> providesUserWithImpactOfUserType() {
        return Stream.of(
                Arguments.of(platinumUser, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_PLATINUM_USERTYPE),
                Arguments.of(goldUser, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_GOLD_USERTYPE),
                Arguments.of(silverUser, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_SILVER_USERTYPE),
                Arguments.of(regularUser, BigDecimal.ONE)
        );
    }

    @ParameterizedTest
    @MethodSource("providesCopiesWithNumberOfDaysFromMovieReleaseToRentalDate")
    void shouldCalculateHowManyDaysPassedFromRelease(Copy copy, int expected) {
        //when
        int result = calculateCostOfCopiesInCart.howManyDaysPassedFromRelease(copy, RENTAL_DATE);
        //then
        assertEquals(expected, result);
    }

    /*
     * number od days in method providesCopiesWithNumberOfDaysFromMovieReleaseToRentalDate from https://www.timeanddate.com/
     */

    private static Stream<Arguments> providesCopiesWithNumberOfDaysFromMovieReleaseToRentalDate() {
        return Stream.of(
                Arguments.of(premiereCopy, 1),
                Arguments.of(newCopy, 62),
                Arguments.of(standardCopy, 215),
                Arguments.of(classicCopy, 5480)
        );
    }

    /*
     * values in next tests with an assumption that:
     * BASIC_COST_FOR_ONE_DAY = 5
     * IMPACT_ON_THE_PRICE_OF_SHORT_TERM_RENTAL = 2
     * IMPACT_ON_THE_PRICE_OF_STANDARD_TERM_RENTAL = 1
     * IMPACT_ON_THE_PRICE_OF_LONG_TERM_RENTAL = 0.5
     * IMPACT_ON_THE_PRICE_OF_SILVER_USERTYPE = 0.95
     * IMPACT_ON_THE_PRICE_OF_GOLD_USERTYPE = 0.85
     * IMPACT_ON_THE_PRICE_OF_PLATINUM_USERTYPE = 0.7
     * MAX_DAYS_FOR_SHORT_TERM_RENTAL = 3
     * MAX_DAYS_FOR_STANDARD_TERM_RENTAL = 6
     * MAX_DAYS_AFTER_RELEASE_FOR_PREMIERE_MOVIE = 14
     * MAX_DAYS_AFTER_RELEASE_FOR_NEW_MOVIE = 90
     * MAX_DAYS_AFTER_RELEASE_FOR_STANDARD_MOVIE = 365
     */

    @ParameterizedTest
    @MethodSource("provideNumberOfRentalDaysAndExpectedCostForShortTermMethod")
    public void shouldCalculateBasicCostForShortTerm(int rentalDays, BigDecimal expected) {
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCostForShortTerm(rentalDays);
        //then
        assertEquals(expected, result);
    }


    private static Stream<Arguments> provideNumberOfRentalDaysAndExpectedCostForShortTermMethod() {
        /*
         * cost for x days = x * 2 (impact of short term) * 5 (basic cost for one day)
         */
        return Stream.of(
                Arguments.of(1, BigDecimal.valueOf(10)), //1*2*5
                Arguments.of(2, BigDecimal.valueOf(20)), //2*2*5
                Arguments.of(3, BigDecimal.valueOf(30)), //3*2*5
                Arguments.of(5, BigDecimal.valueOf(50)), //5*2*5
                Arguments.of(100, BigDecimal.valueOf(1000)), //100*2*5
                Arguments.of(497, BigDecimal.valueOf(4970)) //497*2*5
        );
    }

    @ParameterizedTest
    @MethodSource("provideNumberOfRentalDaysAndExpectedCostForStandardTermMethod")
    public void shouldCalculateBasicCostForStandardTerm(int rentalDays, BigDecimal expected) {
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCostForStandardTerm(rentalDays);
        //then
        assertEquals(expected, result);
    }


    private static Stream<Arguments> provideNumberOfRentalDaysAndExpectedCostForStandardTermMethod() {
        /*
         * cost for x days when x <=3 = x * 2 (impact of short term) * 5 (basic cost for one day) = 10*x
         * cost for x days when x > 3 = 30 (cost of first 3 days) + x * 1 (impact of standard term) * 5 (basic cost for one day) =
         * = 30 + 5*(x-3)
         */
        return Stream.of(
                Arguments.of(1, BigDecimal.valueOf(10)), //1*10
                Arguments.of(2, BigDecimal.valueOf(20)), //2*10
                Arguments.of(3, BigDecimal.valueOf(30)), //3*10
                Arguments.of(4, BigDecimal.valueOf(35)), //30 + 5*1
                Arguments.of(5, BigDecimal.valueOf(40)), //30 + 5*2
                Arguments.of(14, BigDecimal.valueOf(85)), //30 + 5*11
                Arguments.of(100, BigDecimal.valueOf(515)) //30 + 5*97
        );
    }

    @ParameterizedTest
    @MethodSource("provideNumberOfRentalDaysAndExpectedCostForBasicMethod")
    public void shouldCalculateBasicCostWithBasicMethod(int rentalDays, BigDecimal expected) {
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCost(rentalDays);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideNumberOfRentalDaysAndExpectedCostForBasicMethod() {
        /*
         * cost for x days when x <=3 = x * 2 (impact of short term) * 5 (basic cost for one day) = 10*x
         * cost for x days when 3 < x <= 6 = 30 (cost of first 3 days) + x * 1 (impact of standard term) * 5 (basic cost for one day) =
         * = 30 + 5*(x-3)
         * cost for x days when x > 6 = 45 (cost of first 6 days) + x * 0.5 (impact of long term) * 5 (basic cost for one day) =
         * = 45 + x*2.5
         */
        return Stream.of(
                Arguments.of(1, BigDecimal.valueOf(10)), //1*2*5
                Arguments.of(2, BigDecimal.valueOf(20)), //2*2*5
                Arguments.of(3, BigDecimal.valueOf(30)), //3*2*5
                Arguments.of(4, BigDecimal.valueOf(35)), //30 + 5*1
                Arguments.of(5, BigDecimal.valueOf(40)), //30 + 5*2
                Arguments.of(6, BigDecimal.valueOf(45)), //30 + 5*3
                Arguments.of(7, BigDecimal.valueOf(47.5)), //45 + 1*2.5
                Arguments.of(8, BigDecimal.valueOf(50.0)), //45 + 2*2.5
                Arguments.of(14, BigDecimal.valueOf(65.0)), //45 + 8*2.5
                Arguments.of(100, BigDecimal.valueOf(280.0)) //45 + 94*2.5
        );
    }


    @ParameterizedTest
    @MethodSource("provideCopyWithUserAndExpectedCost")
    public void shouldCalculateCostOfCopyWithImpactOfAllFactors(Copy copy, User user, BigDecimal expected) {
        //given
        int rentalDays1 = 1;
        int rentalDays2 = 4;
        int rentalDays3 = 6;
        int rentalDays4 = 18;
        //when
        premiereCopy.setRentalDays(rentalDays1);
        premiereCopy.setRentalDate(RENTAL_DATE);
        newCopy.setRentalDays(rentalDays2);
        newCopy.setRentalDate(RENTAL_DATE);
        standardCopy.setRentalDays(rentalDays3);
        standardCopy.setRentalDate(RENTAL_DATE);
        classicCopy.setRentalDays(rentalDays4);
        classicCopy.setRentalDate(RENTAL_DATE);
        BigDecimal result = calculateCostOfCopiesInCart.calculateCostOfCopy(copy, user);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideCopyWithUserAndExpectedCost() {
        /*
         * Cost of premiere copy rented for 1 day by platinum user = 5 (basic cost) * 1 (number of short terms rental days)
         * * 2 (impact of short term) * 3 (impact of premiere movie) * 0.7 (impact of platinum user) = 21
         * Cost of newCopy rented for 4 days by regular user = (30 (cost for first three days) + 5 (basic cost) * 1
         * (number of standard terms rental days) * 1 (impact of standard term)) * 2 (impact of new movie) * 1
         * (impact of regular user) = (30 + 5) * 2 = 70
         * Cost of standard copy rented for 6 days by silver user = 45 (cost for first six days) * 1 (impact of standard movie) * 0.95
         * (impact of silver user) = 42.75
         * Cost of classic copy rented for 18 days by gold user = (45 (cost for first six days) + (5 (basic cost for one day) * 12
         * (days in long term) * 0.5 (impact of long term))) * 05(impact of classic movie * 0.85 (impact of gold user) =
         * = (45 + 30) * 0.425 = 31.875 = approximately 31.88
         */
        return Stream.of(
                Arguments.of(premiereCopy, platinumUser, BigDecimal.valueOf(21.00).setScale(2, RoundingMode.CEILING)), // 5*1*2*3*0.7
                Arguments.of(newCopy, regularUser, BigDecimal.valueOf(70.00).setScale(2, RoundingMode.CEILING)), // (30 + 5*1*1) * 2 * 1
                Arguments.of(standardCopy, silverUser, BigDecimal.valueOf(42.75)), // 45 * 1 * 0.95
                Arguments.of(classicCopy, goldUser, BigDecimal.valueOf(31.88)) // (45 + 12*5*0.5) * 0.5 * 0.85
        );
    }

    @Test
    public void shouldReturnCostOfZeroWhenCartIsEmpty(){
        //given
        BigDecimal expected = BigDecimal.ZERO;
        //when
        emptyACart.empty(cart);
        calculateCostOfCopiesInCart.calculate(regularUser);
        //then
        assertEquals(expected, cart.getToPay());
    }

@Test
    public void shouldCalculateCostOfCopiesInCartWithOneMovie(){
        //given
    standardCopy.setRentalDate(RENTAL_DATE);
    standardCopy.setRentalDays(3);
    //when
    cart.getCopies().add(standardCopy);
    calculateCostOfCopiesInCart.calculate(regularUser);
    //then
    /*
     * expected cost of Standard Copy = (5 (basic cost) * 3 (number of short terms rental days) * 1 (impact of standard movie)
     * * 2 (impact of short term)) = 30
     * expected total cost of all copies after regular user impact = 30 (total cost) * 1 (regular user impact) = 30
     */
    BigDecimal expected = BigDecimal.valueOf(30).setScale(2, RoundingMode.CEILING);
    assertEquals(expected, cart.getToPay());
    emptyACart.empty(cart);
}

    @Test
    public void shouldCalculateCostOfCopiesInCartWithTwoMovies(){
        //given
        standardCopy.setRentalDate(RENTAL_DATE);
        standardCopy.setRentalDays(3);
        premiereCopy.setRentalDate(RENTAL_DATE);
        premiereCopy.setRentalDays(3);
        //when
        cart.getCopies().add(standardCopy);
        cart.getCopies().add(premiereCopy);
        calculateCostOfCopiesInCart.calculate(silverUser);
        //then
        /*
         * expected cost of Standard Copy = (5 (basic cost) * 3 (number of short terms rental days) * 1 (impact of standard movie)
         * * 2 (impact of short term)) = 30
         * expected cost of Premiere Copy = (5 (basic cost) * 3 (number of short terms rental days) * 3 (impact of standard movie)
         * * 2 (impact of short term)) = 90
         * expected total cost of all copies = 120
         * expected total cost of all copies after silver user impact = 120 (total cost) * 0.95 (silver user impact) = 114
         */
        BigDecimal expected = BigDecimal.valueOf(114).setScale(2, RoundingMode.CEILING); // (30 * 1 + 30 * 3) * 0.95
        assertEquals(expected, cart.getToPay());
        emptyACart.empty(cart);
    }

    @Test
    public void shouldCalculateCostOfCopiesInCartWithThreeMovies(){
        //given
        int numberOfRentalDays = 1;
        classicCopy.setRentalDate(RENTAL_DATE);
        classicCopy.setRentalDays(numberOfRentalDays);
        standardCopy.setRentalDate(RENTAL_DATE);
        standardCopy.setRentalDays(numberOfRentalDays);
        premiereCopy.setRentalDate(RENTAL_DATE);
        premiereCopy.setRentalDays(numberOfRentalDays);
        //when
        cart.getCopies().add(classicCopy);
        cart.getCopies().add(standardCopy);
        cart.getCopies().add(premiereCopy);
        calculateCostOfCopiesInCart.calculate(goldUser);
        //then
        /*
         * expected cost of Classic Copy = 5 (basic cost) * 1 (number of short terms rental days) * 0.5 (impact of classic movie)
         * * 2 (impact of short term) = 5
         * expected cost of Standard Copy = (5 (basic cost) * 1 (number of short terms rental days) * 1 (impact of standard movie)
         * * 2 (impact of short term)) = 10
         * expected cost of Premiere Copy = (5 (basic cost) * 1 (number of short terms rental days) * 3 (impact of premiere movie)
         * * 2 (impact of short term)) = 30
         * expected total cost of all copies = 45
         * expected total cost of all copies after gold user impact = 45 (total cost) * 0.85 (gold user impact) = 38.25
         */
        BigDecimal expectedCost = BigDecimal.valueOf(38.25).setScale(2, RoundingMode.CEILING);
        assertEquals(expectedCost, cart.getToPay());
        emptyACart.empty(cart);
    }

    @Test
    public void shouldCalculateCostOfCopiesInCartWithFourMovies() {
            //given
            int numberOfRentalDaysForClassicCopy = 1;
            int numberOfRentalDaysForStandardCopy = 4;
            int numberOfRentalDaysForNewCopy = 8;
            int numberOfRentalDaysForPremiereCopy = 12;
            classicCopy.setRentalDate(RENTAL_DATE);
            classicCopy.setRentalDays(numberOfRentalDaysForClassicCopy);
            standardCopy.setRentalDate(RENTAL_DATE);
            standardCopy.setRentalDays(numberOfRentalDaysForStandardCopy);
            newCopy.setRentalDate(RENTAL_DATE);
            newCopy.setRentalDays(numberOfRentalDaysForNewCopy);
            premiereCopy.setRentalDate(RENTAL_DATE);
            premiereCopy.setRentalDays(numberOfRentalDaysForPremiereCopy);
            //when
            cart.getCopies().add(classicCopy);
            cart.getCopies().add(standardCopy);
            cart.getCopies().add(newCopy);
            cart.getCopies().add(premiereCopy);
            calculateCostOfCopiesInCart.calculate(platinumUser);
            //then
        /*
         * expected cost of Classic Copy = 5 (basic cost) * 1 (number of short terms rental days) * 0.5 (impact of classic movie)
         * * 2 (impact of short term) = 5
         * expected cost of Standard Copy = (5 (basic cost) * 3 (number of short terms rental days) * 1 (impact of standard movie)
         * * 2 (impact of short term)) + (5 (basic cost) * 1 (number of short terms rental days) * 1 (impact of standard movie)
         * * 1 (impact of standard term)) = 30 + 5 = 35
         * expected cost of New Copy = (5 (basic cost) * 3 (number of short terms rental days) * 2 (impact of new movie)
         * * 2 (impact of short term)) + (5 (basic cost) * 3 (number of short terms rental days) * 2 (impact of new movie)
         * * 1 (impact of standard term)) + (5 (basic cost) * 2 (number of long terms rental days) * 2 (impact of new movie)
         *          * * 0.5 (impact of long term)) = 60 + 30 + 10 = 100
         * expected cost of Premiere Copy = (5 (basic cost) * 3 (number of short terms rental days) * 3 (impact of premiere movie)
         * * 2 (impact of short term)) + (5 (basic cost) * 3 (number of short terms rental days) * 3 (impact of premiere movie)
         * * 1 (impact of standard term)) + (5 (basic cost) * 6 (number of long terms rental days) * 3 (impact of premiere movie)
         *          * * 0.5 (impact of long term)) = 90 + 45 + 45 = 180
         * expected total cost of all copies = 5 + 35 + 100 + 180 = 320
         * expected total cost of all copies after platinum user impact = 320 (total cost) * 0.7 (platinum user impact)
         * = 224
         */
            BigDecimal expectedCost = BigDecimal.valueOf(224)
                    .setScale(2, RoundingMode.CEILING);
            assertEquals(expectedCost, cart.getToPay());
            emptyACart.empty(cart);
        }
    }