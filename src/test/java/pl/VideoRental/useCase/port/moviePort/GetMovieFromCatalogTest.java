package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetMovieFromCatalogTest {

    @Autowired
    GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    CreateMovie createMovie;


    @Test
    public void ShouldGetMovieFromCatalogById() throws MovieDoesNotExistException, MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Batman")
                .genre(Genre.ACTION)
                .description("Batman returned to Gotham...")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .build();
        //when
        createMovie.create(movie);
        Movie movieGotFromCatalog = getMovieFromCatalog.getById(movie.getId());
        //then
        assertEquals(movie.getId(), movieGotFromCatalog.getId());
        assertEquals(movie.getTitle(), movieGotFromCatalog.getTitle());
        assertEquals(movie.getDescription(), movieGotFromCatalog.getDescription());
        assertEquals(movie.getReleaseDate(), movieGotFromCatalog.getReleaseDate());
        assertEquals(movie.getGenre(), movieGotFromCatalog.getGenre());
    }

    @Test
    public void ShouldGetMovieFromCatalogByTitle() throws MovieDoesNotExistException, MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Superman")
                .genre(Genre.ACTION)
                .description("Superman returned to Metropolis...")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .build();
        //when
        createMovie.create(movie);
        Movie movieGotFromCatalog = getMovieFromCatalog.getByTitle(movie.getTitle());
        //then
        assertEquals(movie.getId(), movieGotFromCatalog.getId());
        assertEquals(movie.getTitle(), movieGotFromCatalog.getTitle());
        assertEquals(movie.getDescription(), movieGotFromCatalog.getDescription());
        assertEquals(movie.getReleaseDate(), movieGotFromCatalog.getReleaseDate());
        assertEquals(movie.getGenre(), movieGotFromCatalog.getGenre());
    }

    @Test
    public void ShouldThrowExceptionWhenGettingMovieByIdOfMovieThatNotExist() {
        //given
        long idOfMovieThatNotExist = 1300;
        //then
       assertThrows(MovieDoesNotExistException.class, ()->getMovieFromCatalog.getById(idOfMovieThatNotExist));
    }

    @Test
    public void ShouldThrowExceptionWhenGettingMovieByTitleOfMovieThatNotExist() {
        //given
        String titleOfMovieThatNotExist = "fneaeieaehjShfe2983";
        //then
        assertThrows(MovieDoesNotExistException.class, ()->getMovieFromCatalog.getByTitle(titleOfMovieThatNotExist));
    }


}