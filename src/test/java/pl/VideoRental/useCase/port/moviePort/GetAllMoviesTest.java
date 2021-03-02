package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.sampleData.SampleDataInit;
import pl.VideoRental.domain.Movie;

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

    public static final Movie firstMovie = Movie.builder()
            .title("AAAAA")
            .build();

    public static final Movie lastMovie = Movie.builder()
            .title("ZZZZZ")
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
        Movie movie1 = createMovie.createIfIsNotExisting(firstMovie);
        Movie movie2 = createMovie.createIfIsNotExisting(lastMovie);
        //when
        List<Movie> movies = getAllMovies.getAllInAlphabeticalOrder();
        //then
        assertEquals(firstMovie.getTitle(), movies.get(0).getTitle());
        assertEquals(lastMovie.getTitle(), movies.get(movies.size()-1).getTitle());
        deleteMovie.deleteById(movie1.getId());
        deleteMovie.deleteById(movie2.getId());

    }

}