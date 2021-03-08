package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.copyPort.GetCopyFromCatalog;

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
    @Autowired
    private CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    private GetCopyFromCatalog getCopyFromCatalog;

    private Movie movie = new Movie().builder()
            .title("The return of Sherlock Holmes")
            .genre(Genre.CRIME_STORY)
            .description("Sherlock Holmes returned to London...")
            .releaseDate(LocalDate.of(2000, 1, 1))
            .build();


    @Test
    void shouldDeleteMovieById() throws MovieAlreadyExistException {
        //given
        createMovie.create(movie);
        //when
        deleteMovie.deleteById(movie.getId());
        //then
        assertThrows(MovieDoesNotExistException.class, () -> getMovieFromCatalog.getById(movie.getId()));
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

    //deleting because of orphanRemoval
    @Test
    void shouldDeleteAllCopiesAfterDeletingMovie() throws MovieAlreadyExistException, MovieDoesNotExistException {
        //given
        Movie newMovie = createMovie.create(movie);
        Copy copy = createCopyOfAMovie.create(newMovie.getId());
        //when
        deleteMovie.deleteById(movie.getId());
        //then
        assertThrows(CopyDoesNotExistException.class, () -> getCopyFromCatalog.get(copy.getId()));
    }


}