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

}