package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteMovieFromCatalogTest {

    @Autowired
    private AddMovieToCatalog addMovieToCatalog;
    @Autowired
    private GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    private DeleteMovieFromCatalog deleteMovieFromCatalog;

    private Movie movie = new Movie().builder()
            .title("Sherlock Holmes")
            .genre(Genre.CRIME_STORY)
            .description("Sherlock Holmes returned to London...")
            .build();


    @Test
    void deleteFromCatalogById() throws MovieAlreadyExistException, MovieDoesNotExistException {
        //given
        addMovieToCatalog.add(movie);
        //when
        deleteMovieFromCatalog.deleteById(movie.getId());
        //then
        assertThrows(MovieDoesNotExistException.class, ()-> {getMovieFromCatalog.getById(movie.getId());});
    }

    @Test //TODO - "No EntityManager with actual transaction available for current thread"
    void deleteFromCatalogByTitle() throws MovieAlreadyExistException, MovieDoesNotExistException {
        //given
        addMovieToCatalog.add(movie);
        //when
        deleteMovieFromCatalog.deleteByTitle(movie.getTitle());
        //then
        assertThrows(MovieDoesNotExistException.class, ()-> {getMovieFromCatalog.getByTitle(movie.getTitle());});
    }

    @Test
    void deleteByIdWhenMovieDoesNotExists() {
        //given
        long randomNumber = 7;
        //then
        assertThrows(MovieDoesNotExistException.class, ()-> {deleteMovieFromCatalog.deleteById(randomNumber);});
    }


}