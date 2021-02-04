package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.sampleData.SampleDataStorage;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieIsNotAvailableException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddMovieToCartTest {

    @Autowired
    AddMovieToCart addMovieToCart;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    Cart cart;
    @Autowired
    EmptyACart emptyACart;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    CopyRepository copyRepository;


    @Test
    void shouldAddMovieToCart() throws MovieDoesNotExistException, MovieIsNotAvailableException, CopyIsAlreadyRentedException {
        //given
        User user = getAllUsers.getAll().get(0);
        String movieTitle = SampleDataStorage.MOVIE_1.getTitle();
        long movieId = getMovieFromCatalog.getByTitle(movieTitle).getId();
        int rentalDays = 1;
        //when
        emptyACart.empty(cart);
        addMovieToCart.add(user, movieId, rentalDays);
        //then
        Copy copyFromCart = cart.getCopies().get(0);
        assertNotNull(copyFromCart);
        assertEquals(movieTitle, copyFromCart.getMovie().getTitle());
        assertFalse(copyFromCart.isAvailable());
        assertEquals(LocalDate.now(), copyFromCart.getRentalDate());
        assertEquals(rentalDays, copyFromCart.getRentalDays());
        assertTrue(cart.getToPay().intValue() > 0);
        assertNull(copyFromCart.getUser()); //User is added to Copy during MakeAnOrderFromCartContent process
        emptyACart.empty(cart);

    }

    @Test
    void shouldThrowExceptionWhenMovieIsNotAvailable() throws MovieDoesNotExistException {
        //given
        String movieTitle = SampleDataStorage.MOVIE_3.getTitle();
        long movieId = getMovieFromCatalog.getByTitle(movieTitle).getId();
        Copy copy = getAllCopies.getAll().stream()
                .filter(c -> c.getMovie().getId() == movieId)
                .findFirst().orElse(null); //there is just one copy of third movie
        User user = getAllUsers.getAll().get(0);
        int rentalDays = 1;
        //when
        copy.setAvailable(false);
        copyRepository.save(copy);
        //then
        assertThrows(MovieIsNotAvailableException.class, ()->addMovieToCart.add(user, movieId, rentalDays));
        copy.setAvailable(true);
        copyRepository.save(copy);


    }

}
