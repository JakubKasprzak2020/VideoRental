package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.sampleData.SampleDataInit;
import pl.VideoRental.domain.Movie;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllMoviesTest {

    @Autowired
    private GetAllMovies getAllMovies;
    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private DeleteMovie deleteMovie;

    public static final Movie firstMovieByAlphabet = Movie.builder()
            .title("AAAAA")
            .build();

    public static final Movie lastMovieByAlphabet = Movie.builder()
            .title("ZZZZZ")
            .build();

    public static final Movie firstMovieByChrono = Movie.builder()
            .title("Past")
            .releaseDate(LocalDate.of(1000, 1, 1))
            .build();

    public static final Movie lastMovieByChrono = Movie.builder()
            .title("Future")
            .releaseDate(LocalDate.of(3000, 1, 1))
            .build();

    @Test
    public void shouldGetAllMovies() {
        //given
        int moviesSize = SampleDataInit.moviesLengthMarker;
        //when
        List<Movie> movies = getAllMovies.getAll();
        //then
        assertNotNull(movies);
        assertEquals(moviesSize, movies.size());
    }

    @Test
    public void shouldGetAllMoviesInAlphabeticalOrder(){
        //given
        Movie movie1 = createMovie.createIfIsNotExisting(firstMovieByAlphabet);
        Movie movie2 = createMovie.createIfIsNotExisting(lastMovieByAlphabet);
        //when
        List<Movie> movies = getAllMovies.getAllInAlphabeticalOrder();
        //then
        assertEquals(firstMovieByAlphabet.getTitle(), movies.get(0).getTitle());
        assertEquals(lastMovieByAlphabet.getTitle(), movies.get(movies.size()-1).getTitle());
        deleteMovie.deleteById(movie1.getId());
        deleteMovie.deleteById(movie2.getId());
    }

    @Test
    public void shouldGetAllMoviesInChronologicalOrder(){
        //given
        Movie movie1 = createMovie.createIfIsNotExisting(firstMovieByChrono);
        Movie movie2 = createMovie.createIfIsNotExisting(lastMovieByChrono);
        //when
        List<Movie> movies = getAllMovies.getAllInChronologicalOrder();
        //then
        assertEquals(firstMovieByChrono.getTitle(), movies.get(0).getTitle());
        assertEquals(lastMovieByChrono.getTitle(), movies.get(movies.size()-1).getTitle());
        deleteMovie.deleteById(movie1.getId());
        deleteMovie.deleteById(movie2.getId());
    }

}