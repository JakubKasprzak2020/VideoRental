package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IsMovieExistTest {

    @Autowired
    CreateMovie createMovie;
    @Autowired
    DeleteMovie deleteMovie;
    @Autowired
    IsMovieExist isMovieExist;


    @Test
    void isMovieExistByIdWhenMovieExist() throws MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("The Big Lebowski")
                .genre(Genre.COMEDY)
                .build();
        //when
        createMovie.create(movie);
        boolean result = isMovieExist.isExistById(movie.getId());
        //then
        assertTrue(result);
    }

    @Test
    void isMovieExistByTitleWhenMovieExist() throws MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Shining")
                .genre(Genre.HORROR)
                .build();
        //when
        createMovie.create(movie);
        boolean result = isMovieExist.isExistByTitle(movie.getTitle());
        //then
        assertTrue(result);
    }

    @Test
    void isMovieExistByIdWhenMovieDoesNotExist() {
        //given
        long randomNumber = 18;
        //when
        boolean result = isMovieExist.isExistById(randomNumber);
        //then
        assertFalse(result);
    }

    @Test
    void isMovieExistByTitleWhenMovieDoesNotExist() {
        //given
        String randomTitle = "Monthy Python and the Holy Grail";
        //when
        boolean result = isMovieExist.isExistByTitle(randomTitle);
        //then
        assertFalse(result);
    }


}