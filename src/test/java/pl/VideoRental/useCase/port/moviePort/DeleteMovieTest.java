package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.DeleteMovie;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteMovieTest {

    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    private DeleteMovie deleteMovie;
    @Autowired
    private GetAllMovies getAllMovies;

    private Movie movie = new Movie().builder()
            .title("Sherlock Holmes")
            .genre(Genre.CRIME_STORY)
            .description("Sherlock Holmes returned to London...")
            .releaseDate(LocalDate.of(2000, 1, 1))
            .build();



    @Test
    void shouldDeleteMovieById() throws MovieAlreadyExistException, MovieDoesNotExistException {
        //given
        createMovie.create(movie);
        //when
        deleteMovie.deleteById(movie.getId());
        //then
        assertThrows(MovieDoesNotExistException.class, ()-> getMovieFromCatalog.getById(movie.getId()));
    }


    @Test
    void shouldNotDeleteAnythingWithWrongIdAsAnArgument() {
        //given
        int moviesSizeBeforeDeleting = getAllMovies.getAll().size();
        long movieIdThatNotExist = 1300;
        //when
        deleteMovie.deleteById(movieIdThatNotExist);
        int movieSizeAfterDeleting = getAllMovies.getAll().size();
        //then
        assertEquals(moviesSizeBeforeDeleting, movieSizeAfterDeleting);
    }


}