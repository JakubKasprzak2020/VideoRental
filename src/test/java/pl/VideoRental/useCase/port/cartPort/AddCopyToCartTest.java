package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.userPort.CreateUser;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddCopyToCartTest {

    @Autowired
    AddCopyToCart addCopyToCart;
    @Autowired
    CreateUser createUser;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    CreateMovie createMovie;
    @Autowired
    GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    Cart cart;


//TODO - the same methods are in MakeAnOrderFromCartContentTest - DRY!

    private User createSampleUserAndGetItFromCatalog() {
        UserSignInData userSignInData = UserSignInData.builder()
                .address("Street 8/12 London")
                .email("mail@mail.com")
                .name("John")
                .lastName("Smith")
                .password("password")
                .build();
        createUser.create(userSignInData);
        List<User> users = getAllUsers.getAll();
        return users.get(users.size() - 1);
    }

    private Movie createSampleMovieAndGetItFromCatalog() throws MovieDoesNotExistException, MovieAlreadyExistException {
        Movie movie = Movie.builder().title("Harry Potter").releaseDate(LocalDate.of(2011, 1, 1)).build();
        createMovie.create(movie);
        return getMovieFromCatalog.getByTitle(movie.getTitle());
    }

    private Copy createSampleCopyAndGetItFromCatalog(Movie movie) throws MovieDoesNotExistException {
        createCopyOfAMovie.create(movie.getId());
        List<Copy> copies = getAllCopies.getAllByMovieTitle(movie.getTitle());
        return copies.get(0);
    }


    @Test
    void shouldAddCopyToCart() throws MovieAlreadyExistException, MovieDoesNotExistException, CopyIsAlreadyRentedException {
        //given
        User user = createSampleUserAndGetItFromCatalog();
        Movie movie = createSampleMovieAndGetItFromCatalog();
        Copy copy = createSampleCopyAndGetItFromCatalog(movie);
        //when
        addCopyToCart.add(user, copy, 2, LocalDate.of(2020, 1, 1));
        //then
        Copy copyFromCart = cart.getCopies().get(0);
        assertEquals(copy.getId(), copyFromCart.getId());
        assertEquals(movie.getTitle(), copyFromCart.getMovie().getTitle());
        assertFalse(copyFromCart.isAvailable());
        assertNull(copyFromCart.getUser()); //User is added to Copy during MakeAnOrderFromCartContent process
    }


    @Test
    void cannotAddToCartCopyThatNotExist() {
//TODO
    }

    @Test
    void cannotAddToCartCopyThatIsNotValid() {
//TODO
    }

    static class RemoveACopyFromACartTest {

        //TODO

    }
}