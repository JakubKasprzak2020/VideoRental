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
class DeleteMovieRatingTest {

    @Autowired
    DeleteMovieRating deleteMovieRating;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllMovies getAllMovies;
    @Autowired
    CreateMovieRating createMovieRating;
    @Autowired
    GetAllMovieRatings getAllMovieRatings;

    @Test
    void shouldDeleteMovieRating() throws MovieDoesNotExistException {
        //given
        User user = getAllUsers.getAll().get(0);
        long movieId = getAllMovies.getAll().get(0).getId();
        int rating = 5;
        //when
        MovieRating movieRating = createMovieRating.create(user, movieId, rating);
        //then
        deleteMovieRating.delete(movieRating.getId());
        MovieRating savedMovieRating = getAllMovieRatings.getAll().stream()
                .filter(m  -> m.getId() == movieRating.getId())
                .findFirst()
                .orElse(null);
        assertNull(savedMovieRating);
    }

}