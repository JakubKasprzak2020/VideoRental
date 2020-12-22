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

    private static User regularUser = User.builder().name("RegularUser").userType(UserType.REGULAR).build();
    private static User silverUser = User.builder().name("SilverUser").userType(UserType.SILVER).build();
    private static User goldUser = User.builder().name("GoldUser").userType(UserType.GOLD).build();
    private static User platinumUser = User.builder().name("PlatinumUser").userType(UserType.PLATINUM).build();

    private static Movie premiereMovie = Movie.builder().title("Premiere Movie").releaseDate(LocalDate.of(2020, 12, 1)).build();
    private static Movie newMovie = Movie.builder().title("New Movie").releaseDate(LocalDate.of(2020, 10, 1)).build();
    private static Movie standardMovie = Movie.builder().title("Standard Movie").releaseDate(LocalDate.of(2020, 5, 1)).build();
    private static Movie classicMovie = Movie.builder().title("Classic Movie").releaseDate(LocalDate.of(2005, 12, 1)).build();

    private static Copy premiereCopy = Copy.builder().movie(premiereMovie).build();
    private static Copy newCopy = Copy.builder().movie(newMovie).build();
    private static Copy standardCopy = Copy.builder().movie(standardMovie).build();
    private static Copy classicCopy = Copy.builder().movie(classicMovie).build();

    private static final LocalDate RENTALDATE = LocalDate.of(2020, 12, 2);


    @ParameterizedTest
    @MethodSource("providesCopiesWithImpactOfDateRelease")
    void shouldGiveImpactOfDatesReleases(Copy copy, BigDecimal expected){
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
    void shouldGiveImpactOfUsersTypes(User user, BigDecimal expected){
        //when
        BigDecimal result = calculateCostOfCopiesInCart.getImpactOfUserType(user);
        //then
        assertEquals(expected, result);
    }

    private static Stream<Arguments> providesUserWithImpactOfUserType(){
        return Stream.of(
                Arguments.of(platinumUser, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_PLATINUM_USERTYPE),
                Arguments.of(goldUser, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_GOLD_USERTYPE),
                Arguments.of(silverUser, CalculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_SILVER_USERTYPE),
                Arguments.of(regularUser, BigDecimal.ONE)
        );
    }

    @ParameterizedTest
    @MethodSource("providesCopiesWithNumberOfDaysFromMovieReleaseToRentalDate")
    void shouldCalculateHowManyDaysPassedFromRelease(Copy copy, int expected){
        //when
        int result = calculateCostOfCopiesInCart.howManyDaysPassedFromRelease(copy, RENTALDATE);
        //then
        assertEquals(expected, result);
    }

    /**
     *number od days in method providesCopiesWithNumberOfDaysFromMovieReleaseToRentalDate from https://www.timeanddate.com/
     */

    private static Stream<Arguments> providesCopiesWithNumberOfDaysFromMovieReleaseToRentalDate(){
        return Stream.of(
                Arguments.of(premiereCopy, 1),
                Arguments.of(newCopy, 62),
                Arguments.of(standardCopy, 215),
                Arguments.of(classicCopy, 5480)
        );
    }

    @Test
    public void shouldCalculateBasicCostForShortTerm(){
        //given
        int rentalDays = 2;
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCostForShortTerm(rentalDays);
        BigDecimal expected = BigDecimal.valueOf(20); // rental days * impactOfRelays * basic cost = 2 * 2 * 5
        //then
        assertEquals(expected, result);
    }

    @Test
    public void shouldCalculateBasicCostForShortTermWithBasicMethod(){
        //given
        int rentalDays = 2;
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCost(rentalDays);
        BigDecimal expected = BigDecimal.valueOf(20); // rental days * impactOfRelays * basic coste = 2 * 2 * 5
        //then
        assertEquals(expected, result);
    }

    @Test
    public void shouldCalculateBasicCostForStandardTerm(){
        //given
        int rentalDays = 5;
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCostForStandardTerm(rentalDays);
        BigDecimal expected = BigDecimal.valueOf(40); // 3 * 2 * 5 + 2 * 1 * 5
        //then
        assertEquals(expected, result);
    }

    @Test
    public void shouldCalculateBasicCostForStandardTermWithBasicMethod(){
        //given
        int rentalDays = 5;
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCost(rentalDays);
        BigDecimal expected = BigDecimal.valueOf(40); // 3 * 2 * 5 + 2 * 1 * 5
        //then
        assertEquals(expected, result);
    }

    @Test
    public void shouldCalculateBasicCostForLongTermWithBasicMethod(){
        //given
        int rentalDays = 8;
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCost(rentalDays);
        BigDecimal expected = BigDecimal.valueOf(50.0); // 3 * 2 * 5 + 3 * 1 * 5 + 2 * 0.5 * 5 = 30 + 15 + 5
        //then
        assertEquals(expected, result);
    }


    @Test
    public void shouldCalculateCostOfCopyOfPremiereMovieForPlatinumUser(){
        //given
        int rentalDays = 8;
        premiereCopy.setUser(platinumUser);
        premiereCopy.setRentalDays(rentalDays);
        premiereCopy.setRentalDate(RENTALDATE);
        //when
        int resultInInt = calculateCostOfCopiesInCart.calculateCostOfCopy(premiereCopy, platinumUser).intValue();
        //then
        int expectedInInt = 105; // 50 * 3 * 0.7
        assertEquals(expectedInInt, resultInInt); //int Value because in Big Decimal Expected was 105.0 and Actual:105.00
    }

    @Test //TODO - test is not passing correctly, gives 60
    public void shouldMakeCartSummaryForRegularUser() {
        //given
        premiereCopy.setUser(regularUser);
        premiereCopy.setRentalDays(1);
        premiereCopy.setRentalDate(RENTALDATE);
        classicCopy.setUser(regularUser);
        classicCopy.setRentalDays(1);
        classicCopy.setRentalDate(RENTALDATE);
       // Cart cart = calculateCostOfCopiesInCart.getCart();
        cart.getCopies().add(premiereCopy);
        cart.getCopies().add(classicCopy);
        //when
        calculateCostOfCopiesInCart.calculate(regularUser);
        int result = cart.getToPay().intValue();
        int expected = 35;
        //then
        assertEquals(expected, result);
    }



    //TODO create more complex tests

}