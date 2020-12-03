package pl.VideoRental.useCase.port;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.Assert;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class AddMovieToCatalogTest {

    @Autowired
    private AddMovieToCatalog addMovieToCatalog;
    @Autowired
    private GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    private DeleteMovieFromCatalog deleteMovieFromCatalog;

    private Movie movie = new Movie().builder()
            .title("Batman")
            .genre(Genre.ACTION)
            .description("Batman returned to Gotham...")
            .build();

    @BeforeEach
    void clearDataBase(){
        try {
            deleteMovieFromCatalog.deleteById(movie.getId());
        } catch (MovieDoesNotExist exception) {
        }
    }


    //TODO - Tests passed only singly

    @Test
    void addNewMovieToCatalog() throws MovieAlreadyExistException, MovieDoesNotExist {
        //when
        addMovieToCatalog.add(movie);
        Movie batman = getMovieFromCatalog.getByTitle(movie.getTitle());
        //then
        assertEquals(movie.getTitle(), batman.getTitle());
        assertEquals(movie.getGenre(), batman.getGenre());
        assertEquals(movie.getDescription(), batman.getDescription());
    }

    @Test
    void addAlreadyExistedMovieToCatalog() throws MovieAlreadyExistException {
        //when
        addMovieToCatalog.add(movie);
        //then
        assertThrows(MovieAlreadyExistException.class, ()-> {addMovieToCatalog.add(movie);});
    }


}