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
import pl.VideoRental.useCase.port.orderPort.GetAllOrders;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.userPort.CreateUser;

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
    private CreateMovie createMovie;
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
        UserSignInData userSignInData = UserSignInData.builder()
                .address("Street 8/12 London")
                .email("mail@mail.com")
                .name("John")
                .lastName("Smith")
                .password("password")
                .build();
        createUser.create(userSignInData);
        List<User> users = getAllUsers.getAll();
        return users.get(users.size()-1);
    }

    private Movie createSampleMovieAndGetItFromCatalog() throws MovieDoesNotExistException, MovieAlreadyExistException {
        Movie movie = Movie.builder().title("Harry Potter").releaseDate(LocalDate.of(2011, 1, 1)).build();
        createMovie.create(movie);
        return getMovieFromCatalog.getByTitle(movie.getTitle());
    }

    private Copy createSampleCopyAndGetItFromCatalog(Movie movie) throws MovieDoesNotExistException {
        createCopyOfAMovie.create(movie.getId());
        List <Copy> copies = getAllCopies.getAllByMovieTitle(movie.getTitle());
        return copies.get(0);
    }


    @Test
    public void shouldMakeAnOrder() throws MovieAlreadyExistException, MovieDoesNotExistException, CopyIsAlreadyRentedException {
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