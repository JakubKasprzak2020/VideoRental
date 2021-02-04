package pl.VideoRental.useCase.port.movieRatingPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.MovieRating;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateMovieRatingTest {

    @Autowired
    CreateMovieRating createMovieRating;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllMovies getAllMovies;
    @Autowired
    GetAllMovieRatings getAllMovieRatings;
    @Autowired
    DeleteMovieRating deleteMovieRating;

    @Test
    void shouldCreateMovieRating() throws MovieDoesNotExistException {
        //given
        User user = getAllUsers.getAll().get(0);
        long movieId = getAllMovies.getAll().get(0).getId();
        int rating = 5;
        //when
        MovieRating movieRating = createMovieRating.create(user, movieId, rating);
        //then
        MovieRating savedMovieRating = getAllMovieRatings.getAll().stream()
                .filter(m  -> m.getId() == movieRating.getId())
                .findFirst()
                .orElse(null);
        assertNotNull(savedMovieRating);
        assertEquals(user.getId(), savedMovieRating.getUserId());
        assertEquals(movieId, savedMovieRating.getMovieId());
        assertEquals(rating, savedMovieRating.getRating());
        deleteMovieRating.delete(savedMovieRating.getId());
    }

    @Test
    void shouldThrowExceptionWhenRatingIsOutOfRange(){
        //given
        User user = getAllUsers.getAll().get(0);
        long movieId = getAllMovies.getAll().get(0).getId();
        int rating = 100;
        //when
        assertThrows(IllegalArgumentException.class, () -> createMovieRating.create(user, movieId, rating));
    }

    @Test
    void shouldThrowExceptionWhenMovieIdIsWrong(){
        //given
        User user = getAllUsers.getAll().get(0);
        long movieId = 3000;
        int rating = 5;
        //when
        assertThrows(MovieDoesNotExistException.class, () -> createMovieRating.create(user, movieId, rating));
    }

}