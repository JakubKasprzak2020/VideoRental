package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.getAllUtils.GetAllCopies;
import pl.VideoRental.useCase.port.getAllUtils.GetAllUsers;

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
    AddMovieToCatalog addMovieToCatalog;
    @Autowired
    GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    GetAllCopies getAllCopies;


//TODO - the same methods are in MakeAnOrderFromCartContentTest - DRY!

    private User createSampleUserAndGetItFromCatalog() {
        createUser.create("John", "Smith", "password", "mail@mail.com", "Street 8/12 London");
        List<User> users = getAllUsers.getAll();
        return users.get(users.size() - 1);
    }

    private Movie createSampleMovieAndGetItFromCatalog() throws MovieDoesNotExistException, MovieAlreadyExistException {
        Movie movie = Movie.builder().title("Harry Potter").releaseDate(LocalDate.of(2011, 1, 1)).build();
        addMovieToCatalog.add(movie);
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
        Copy copyFromCart = user.getCart().getCopies().get(0);
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
}