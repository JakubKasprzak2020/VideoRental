package pl.VideoRental.useCase.port.movieRatingPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.domain.MovieRating;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CountAverageRatingTest {

    @Autowired
    private CountAverageRating countAverageRating;
    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private GetAllUsers getAllUsers;
    @Autowired
    private CreateMovieRating createMovieRating;


    @Test
    void shouldCountAverageRating() throws MovieDoesNotExistException {
        //given
        long movieId = createMovie.createIfIsNotExisting(Movie.builder().title("Test Movie").build()).getId();
        User user = getAllUsers.getAll().get(0);
        int firstMark = 10;
        int secondMark = 6;
        int thirdMark = 2;
        int forthMark = 4;
        MovieRating movieRating1 = createMovieRating.create(user, movieId, firstMark);
        MovieRating movieRating2 = createMovieRating.create(user, movieId, secondMark);
        MovieRating movieRating3 = createMovieRating.create(user, movieId, thirdMark);
        MovieRating movieRating4 = createMovieRating.create(user, movieId, forthMark);
        //when
        double result = countAverageRating.count(movieId);
        //then
        assertEquals(5.5, result); // 10+6+2+4 / 4 = 22/4 = 5.5
    }

    @Test
    void shouldThrowExceptionWhithWrongMovieId(){
        //given
        long wrongMovieId = 877;
        //then
        assertThrows(MovieDoesNotExistException.class, () -> countAverageRating.count(wrongMovieId));
    }


}