package pl.VideoRental.useCase.port.movieRatingPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.MovieRating;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllMovieRatingsTest {

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
    void shouldGetAllMovieRatings() throws MovieDoesNotExistException {
        //given
        User user = getAllUsers.getAll().get(0);
        long movieId = getAllMovies.getAll().get(0).getId();
        int rating = 5;
        //when
        MovieRating mr = createMovieRating.create(user, movieId, rating);
        List<MovieRating> movieRatings = getAllMovieRatings.getAll();
        //then
        assertNotNull(movieRatings);
        MovieRating savedMovieRating = getAllMovieRatings.getAll().stream()
                .filter(m  -> m.getId() == mr.getId())
                .findFirst()
                .orElse(null);
        assertNotNull(savedMovieRating);
        assertEquals(mr.getId(), savedMovieRating.getId());
        deleteMovieRating.delete(mr.getId());
    }


}