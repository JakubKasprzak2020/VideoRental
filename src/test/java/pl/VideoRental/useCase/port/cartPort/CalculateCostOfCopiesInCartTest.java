package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateCostOfCopiesInCartTest {

    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private CreateCopyOfAMovie createCopyOfAMovie;
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

    private static final LocalDate RENTALDATE = LocalDate.of(2020, 12, 2);


    @ParameterizedTest
    @MethodSource("providesCopiesWithImpactOfDateRelease")
    void shouldGiveImpactOfDatesReleases(Copy copy, BigDecimal expected) {
        //when
        BigDecimal result = calculateCostOfCopiesInCart.getImpactOfDateRelease(copy, RENTALDATE);
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
        int result = calculateCostOfCopiesInCart.howManyDaysPassedFromRelease(copy, RENTALDATE);
        //then
        assertEquals(expected, result);
    }

    /**
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

    /**
     * values in next tests with an assumption that
     * BASIC_COST_FOR_ONE_DAY = 5
     * IMPACT_ON_THE_PRICE_OF_SHORT_TERM_RENTAL = 2
     * IMPACT_ON_THE_PRICE_OF_STANDARD_TERM_RENTAL = 1
     * IMPACT_ON_THE_PRICE_OF_LONG_TERM_RENTAL = 0.5
     * MAX_DAYS_FOR_SHORT_TERM_RENTAL = 3
     * MAX_DAYS_FOR_STANDARD_TERM_RENTAL = 6
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
        return Stream.of(
                Arguments.of(1, BigDecimal.valueOf(10)), //1*2*5
                Arguments.of(2, BigDecimal.valueOf(20)), //2*2*5
                Arguments.of(3, BigDecimal.valueOf(30)), //3*2*5
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
        return Stream.of(
                Arguments.of(1, BigDecimal.valueOf(10)), //1*2*5
                Arguments.of(2, BigDecimal.valueOf(20)), //2*2*5
                Arguments.of(3, BigDecimal.valueOf(30)), //3*2*5
                Arguments.of(4, BigDecimal.valueOf(35)), //30 + 5*1
                Arguments.of(5, BigDecimal.valueOf(40)), //30 + 5*2
                Arguments.of(6, BigDecimal.valueOf(45)), //30 + 5*3
                Arguments.of(7, BigDecimal.valueOf(47.5)), //45 + 5*0.5*1
                Arguments.of(8, BigDecimal.valueOf(50.0)), //45 + 5*0.5*2
                Arguments.of(14, BigDecimal.valueOf(65.0)), //45 + 5*0.5*8
                Arguments.of(100, BigDecimal.valueOf(280.0)) //45 + 5*0.5*94
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
        premiereCopy.setRentalDate(RENTALDATE);
        newCopy.setRentalDays(rentalDays2);
        newCopy.setRentalDate(RENTALDATE);
        standardCopy.setRentalDays(rentalDays3);
        standardCopy.setRentalDate(RENTALDATE);
        classicCopy.setRentalDays(rentalDays4);
        classicCopy.setRentalDate(RENTALDATE);
        BigDecimal result = calculateCostOfCopiesInCart.calculateCostOfCopy(copy, user);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> provideCopyWithUserAndExpectedCost() {
        return Stream.of(
                Arguments.of(premiereCopy, platinumUser, BigDecimal.valueOf(21.00).setScale(2, RoundingMode.CEILING)), // 5*1*2*3*0.7
                Arguments.of(newCopy, regularUser, BigDecimal.valueOf(70.00).setScale(2, RoundingMode.CEILING)), // (30 + 5*1*1) * 2 * 1
                Arguments.of(standardCopy, silverUser, BigDecimal.valueOf(42.75)), // 45 * 1 * 0.95
                Arguments.of(classicCopy, goldUser, BigDecimal.valueOf(31.88)) // (45 + 12*5*0.5) * 0.5 * 0.85
        );
    }

@Test
    public void shouldCalculateCostOfCopiesInCartWithOneMovie(){
        //given
    standardCopy.setRentalDate(RENTALDATE);
    standardCopy.setRentalDays(3);
    //when
    cart.getCopies().add(standardCopy);
    calculateCostOfCopiesInCart.calculate(regularUser);
    //then
    BigDecimal expected = BigDecimal.valueOf(30).setScale(2, RoundingMode.CEILING); // 30 * 1 * 1
    assertEquals(expected, cart.getToPay());
    emptyACart.empty(cart);
}

    @Test
    public void shouldCalculateCostOfCopiesInCartWithTwoMovies(){
        //given
        standardCopy.setRentalDate(RENTALDATE);
        standardCopy.setRentalDays(3);
        premiereCopy.setRentalDate(RENTALDATE);
        premiereCopy.setRentalDays(3);
        //when
        cart.getCopies().add(standardCopy);
        cart.getCopies().add(premiereCopy);
        calculateCostOfCopiesInCart.calculate(silverUser);
        //then
        BigDecimal expected = BigDecimal.valueOf(114).setScale(2, RoundingMode.CEILING); // (30 * 1 + 30 * 3) * 0.95
        assertEquals(expected, cart.getToPay());
        emptyACart.empty(cart);
    }

    @Test
    public void shouldCalculateCostOfCopiesInCartWithThreeMovies(){
        //given
        //when
        //then
        emptyACart.empty(cart);
    }

    @Test
    public void shouldCalculateCostOfCopiesInCartWithFourMovies(){
        //given
        //when
        //then
        emptyACart.empty(cart);
    }



}