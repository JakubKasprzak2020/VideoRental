package pl.VideoRental.useCase.port.movieRatingPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllAverageRatingsTest {

    @Autowired
    GetAllAverageRatings getAllAverageRatings;
    @Autowired
    GetAllMovies getAllMovies;

    @Test
    void shouldGetAllAverageRatings() throws MovieDoesNotExistException {
        //when
        Map<String, Double> result = getAllAverageRatings.getAll();
        //then
        assertNotNull(result);
        assertEquals(getAllMovies.getAll().size(), result.size());
        System.out.println(result);
    }
}