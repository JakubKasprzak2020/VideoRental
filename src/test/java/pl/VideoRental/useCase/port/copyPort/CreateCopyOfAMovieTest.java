package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateCopyOfAMovieTest {

    @Autowired
    private GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    private IsCopyExist isCopyExist;
    @Autowired
    private GetCopyFromCatalog getCopyFromCatalog;

    @Test
    void createCopyOfAMovie() throws MovieAlreadyExistException, MovieDoesNotExistException, CopyDoesNotExistException {
        //given
        long expectedValueofIdOfCopy = 2; //with generatedValue Strategy = Auto (movie id = 1, copy id = 2)
        Movie movie = Movie.builder()
                .title("GhostBusters")
                .genre(Genre.COMEDY)
                .build();
        createMovie.create(movie);
        //when
        createCopyOfAMovie.create(movie.getId());
        boolean result = isCopyExist.isExistById(expectedValueofIdOfCopy);
        //then
        assertTrue(result);
        assertEquals(movie.getTitle(), getCopyFromCatalog.get(expectedValueofIdOfCopy).getMovie().getTitle());
    }

    @Test
    void createCopyOfAMovieThatDoesNotExist(){
        //given
        long randomNumber = 87;
        //then
        assertThrows(MovieDoesNotExistException.class, () -> {
            createCopyOfAMovie.create(randomNumber);
        });

    }

}