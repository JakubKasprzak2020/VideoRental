package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IsMovieExistTest {

    @Autowired
    CreateMovie createMovie;
    @Autowired
    IsMovieExist isMovieExist;
    @Autowired
    DeleteMovie deleteMovie;


    @Test
    void shouldReturnTrueWhenSearchingByIdWhenMovieExists() throws MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("The Big Lebowski")
                .genre(Genre.COMEDY)
                .releaseDate(LocalDate.of(2000, 1, 1))
                .description("Comedy of the Coen Brothers.")
                .build();
        //when
        createMovie.create(movie);
        boolean result = isMovieExist.isExistById(movie.getId());
        //then
        assertTrue(result);
        deleteMovie.deleteById(movie.getId());
    }

    @Test
    void shouldReturnTrueWhenSearchingByTitleWhenMovieExists() throws MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Shining")
                .genre(Genre.HORROR)
                .releaseDate(LocalDate.of(2000, 1, 1))
                .description("Horror movie made by Stanley Kubrick.")
                .build();
        //when
        createMovie.create(movie);
        boolean result = isMovieExist.isExistByTitle(movie.getTitle());
        //then
        assertTrue(result);
        deleteMovie.deleteById(movie.getId());
    }

    @Test
    void shouldReturnFalseWhenSearchingByIdWhenMovieDoesNotExists() {
        //given
        long idOfMovieThatDoesNotExist = 2500;
        //when
        boolean result = isMovieExist.isExistById(idOfMovieThatDoesNotExist);
        //then
        assertFalse(result);
    }

    @Test
    void shouldReturnFalseWhenSearchingByTitleWhenMovieDoesNotExists() {
        //given
        String titleOfMovieThatDoesNotExist = "AAAAAAAAAAA87654gbhjnea6HSHAe";
        //when
        boolean result = isMovieExist.isExistByTitle(titleOfMovieThatDoesNotExist);
        //then
        assertFalse(result);
    }


}