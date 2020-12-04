package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    private Copy premiereCopy = Copy.builder().movie(premiereMovie).rentDate(LocalDate.of(2020, 12, 02)).build();
    private Copy newCopy = Copy.builder().movie(newMovie).rentDate(LocalDate.of(2020, 12, 02)).build();
    private Copy standardCopy = Copy.builder().movie(standardMovie).rentDate(LocalDate.of(2020, 12, 02)).build();
    private Copy classicCopy = Copy.builder().movie(classicMovie).rentDate(LocalDate.of(2020, 12, 02)).build();


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
    void calculateCost() {
        //given
        Cart cart = regularUser.getCart();
        //when
        standardCopy.setRentalDays(1);
        List <Copy> copies = new ArrayList<>();
        cart.setCopies(copies);
        standardCopy.setUser(regularUser);
       cart.getCopies().add(standardCopy);
        calculateCostOfCopiesInCart.calculate(cart);
        //then
        BigDecimal result = cart.getToPay();
        BigDecimal expected = BigDecimal.TEN;
        assertEquals(expected, result);
    }


    //TODO

}