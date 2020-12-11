package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CalculateCostOfCopiesInCartTest {

    @Autowired
    private AddMovieToCatalog addMovieToCatalog;
    @Autowired
    private CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    private CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;

    private User regularUser = User.builder().name("RegularUser").userType(UserType.REGULAR).cart(new Cart()).build();
    private User silverUser = User.builder().name("SilverUser").userType(UserType.SILVER).cart(new Cart()).build();
    private User goldUser = User.builder().name("GoldUser").userType(UserType.GOLD).cart(new Cart()).build();
    private User platinumUser = User.builder().name("PlatinumUser").userType(UserType.PLATINUM).cart(new Cart()).build();

    private Movie premiereMovie = Movie.builder().title("Premiere Movie").releaseDate(LocalDate.of(2020, 12, 01)).build();
    private Movie newMovie = Movie.builder().title("New Movie").releaseDate(LocalDate.of(2020, 10, 01)).build();
    private Movie standardMovie = Movie.builder().title("Standard Movie").releaseDate(LocalDate.of(2020, 05, 01)).build();
    private Movie classicMovie = Movie.builder().title("Classic Movie").releaseDate(LocalDate.of(2005, 12, 01)).build();
    private Copy premiereCopy = Copy.builder().movie(premiereMovie).build();
    private Copy newCopy = Copy.builder().movie(newMovie).build();
    private Copy standardCopy = Copy.builder().movie(standardMovie).build();
    private Copy classicCopy = Copy.builder().movie(classicMovie).build();

    private static final LocalDate RENTALDATE = LocalDate.of(2020, 12, 02);

/*    @BeforeAll
    void addMoviesAndCreateCopies() throws MovieAlreadyExistException, MovieDoesNotExist {
        addMovieToCatalog.add(premiereMovie);
        addMovieToCatalog.add(newMovie);
        addMovieToCatalog.add(standardMovie);
        addMovieToCatalog.add(classicMovie);
        createCopyOfAMovie.create(1);
        createCopyOfAMovie.create(2);
        createCopyOfAMovie.create(3);
        createCopyOfAMovie.create(4);
    }*/


    @Test
    void shouldGiveImpactOfDatesReleases(){
        //when
        BigDecimal result = calculateCostOfCopiesInCart.getImpactOfDateRelease(premiereCopy, RENTALDATE);
        //then
        BigDecimal expected = calculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_PREMIERE_MOVIE;
        assertEquals(expected, result);
    }


   @Test
    void shouldGiveImpactOfUsersTypes(){
        //when
        BigDecimal result = calculateCostOfCopiesInCart.getImpactOfUserType(silverUser); // copy not user as an argument
        //then
        BigDecimal expected = calculateCostOfCopiesInCart.IMPACT_ON_THE_PRICE_OF_SILVER_USERTYPE;
        assertEquals(expected, result);
    }

    @Test
    void shouldCalculatehowManyDaysPassedFromRelease(){
        //when
        int result = calculateCostOfCopiesInCart.howManyDaysPassedFromRelease(premiereCopy, RENTALDATE);
        //then
        int expected = 1;
        assertEquals(expected, result);
    }

    @Test
    public void shouldCalculateBasicCostForShortTerm(){
        //given
        int rentalDays = 2;
        //when
        BigDecimal result = calculateCostOfCopiesInCart.calculateBasicCostForShortTerm(rentalDays);
        BigDecimal expected = BigDecimal.valueOf(20); // rental days * impactOfRelays * basic coste = 2 * 2 * 5
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
        Cart cart = regularUser.getCart();
        cart.getCopies().add(premiereCopy);
        cart.getCopies().add(classicCopy);
        //when
        calculateCostOfCopiesInCart.calculate(regularUser);
        BigDecimal result = cart.getToPay();
        BigDecimal expected = BigDecimal.valueOf(35);
        //then
        assertEquals(expected, result);
    }



    //TODO create more complex tests

}