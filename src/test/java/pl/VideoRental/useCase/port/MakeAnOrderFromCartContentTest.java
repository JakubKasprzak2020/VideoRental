package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRented;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;
import pl.VideoRental.useCase.port.getAllUtils.GetAllCopies;
import pl.VideoRental.useCase.port.getAllUtils.GetAllOrders;
import pl.VideoRental.useCase.port.getAllUtils.GetAllUsers;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MakeAnOrderFromCartContentTest {

    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetAllUsers getAllUsers;
    @Autowired
    private AddMovieToCatalog addMovieToCatalog;
    @Autowired
    private CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    private GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    private GetAllCopies getAllCopies;
    @Autowired
    private AddCopyToCart addCopyToCart;
    @Autowired
    private MakeAnOrderFromCartContent makeAnOrderFromCartContent;
    @Autowired
    private GetAllOrders getAllOrders;


    private User createSampleUserAndGetItFromCatalog(){
        createUser.create("John", "Smith", "password", "mail@mail.com", "Street 8/12 London");
        List<User> users = getAllUsers.getAll();
        return users.get(users.size()-1);
    }

    private Movie createSampleMovieAndGetItFromCatalog() throws MovieDoesNotExist, MovieAlreadyExistException {
        Movie movie = Movie.builder().title("Harry Potter").releaseDate(LocalDate.of(2011, 1, 1)).build();
        addMovieToCatalog.add(movie);
        return getMovieFromCatalog.getByTitle(movie.getTitle());
    }

    private Copy createSampleCopyAndGetItFromCatalog(Movie movie) throws MovieDoesNotExist {
        createCopyOfAMovie.create(movie.getId());
        List <Copy> copies = getAllCopies.getAllByMovieTitle(movie.getTitle());
        return copies.get(0);
    }


    @Test
    public void shouldMakeAnOrder() throws MovieAlreadyExistException, MovieDoesNotExist, CopyIsAlreadyRented {
        //given
        User user = createSampleUserAndGetItFromCatalog();
        Movie movie = createSampleMovieAndGetItFromCatalog();
        Copy copy = createSampleCopyAndGetItFromCatalog(movie);
        addCopyToCart.add(user, copy, 1, LocalDate.of(2020, 1, 1));
        //when
        makeAnOrderFromCartContent.makeAnOrder(user);
        Order order = user.getOrders().get(0);
        //then
        assertEquals(user.getName(), order.getUser().getName());
        assertEquals(1, order.getCopies().size());
        assertEquals(movie.getTitle(), order.getCopies().get(0).getMovie().getTitle());
        assertNull(order.getDelivery());
    }


}