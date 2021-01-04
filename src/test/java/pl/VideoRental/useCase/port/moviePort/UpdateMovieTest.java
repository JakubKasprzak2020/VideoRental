package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.SampleData.SampleDataStorage;
import pl.VideoRental.domain.Movie;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateMovieTest {

    @Autowired
    UpdateMovie updateMovie;
    @Autowired
    IsMovieExist isMovieExist;

    Movie movie = Movie.builder()
            .title("Update Title")
            .description(SampleDataStorage.MOVIE_1.getDescription())
            .releaseDate(SampleDataStorage.MOVIE_1.getReleaseDate())
            .genre(SampleDataStorage.MOVIE_1.getGenre())
            .build();

    @Test
    void shouldUpdateMovie() {
        //given
        long movieId = SampleDataStorage.MOVIE_1.getId();
        String oldMovieTitle = SampleDataStorage.MOVIE_1.getTitle();
        //when
        updateMovie.update(movieId, movie);
        //then
        assertTrue(isMovieExist.isExistByTitle(movie.getTitle()));
        assertFalse(isMovieExist.isExistByTitle(oldMovieTitle));
        movie.setTitle(oldMovieTitle);
        updateMovie.update(movieId, movie);
    }


    @Test
    void shouldNotUpdateMovieWhenIdIsIncorrect() {
        //given
        long idOfMovieThatDoesNotExist = 560;
        //when
        updateMovie.update(idOfMovieThatDoesNotExist, movie);
        //then
        assertFalse(isMovieExist.isExistByTitle(movie.getTitle()));
    }


}