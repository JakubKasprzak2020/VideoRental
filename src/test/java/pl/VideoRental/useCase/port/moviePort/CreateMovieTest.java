package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

//@ExtendWith(SpringExtension.class)
//@DataJpaTest
@SpringBootTest
class CreateMovieTest {

    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    private DeleteMovie deleteMovie;



    @Test
    void addNewMovieToCatalog() throws MovieAlreadyExistException, MovieDoesNotExistException {
        //given
        Movie movie = Movie.builder()
                .title("Batman")
                .genre(Genre.ACTION)
                .description("Batman returned to Gotham...")
                .build();
        //when
        createMovie.create(movie);
        Movie batman = getMovieFromCatalog.getByTitle(movie.getTitle());
        //then
        assertEquals(movie.getTitle(), batman.getTitle());
        assertEquals(movie.getGenre(), batman.getGenre());
        assertEquals(movie.getDescription(), batman.getDescription());
    }

    @Test
    void addAlreadyExistedMovieToCatalog() throws MovieAlreadyExistException {
        //given
        Movie movie = Movie.builder()
                .title("Birdman")
                .genre(Genre.DRAMA)
                .description("An actor played Super Hero Birdman...")
                .build();
        //when
        createMovie.create(movie);
        //then
        assertThrows(MovieAlreadyExistException.class, () -> {
            createMovie.create(movie);
        });
    }


}