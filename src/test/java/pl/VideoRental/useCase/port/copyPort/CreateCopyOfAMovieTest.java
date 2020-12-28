package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;


import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateCopyOfAMovieTest {

    @Autowired
    private CreateMovie createMovie;
    @Autowired
    private CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    private IsCopyExist isCopyExist;

    @Test
    void shouldCreateCopyOfAMovie() throws MovieAlreadyExistException, MovieDoesNotExistException {
        //given
        Movie movie = Movie.builder()
                .title("GhostBusters")
                .genre(Genre.COMEDY)
                .releaseDate(LocalDate.of(2000, 1, 1))
                .description("Who can help when ghosts are attacking a city?")
                .build();
        createMovie.create(movie);
        //when
        Copy copy = createCopyOfAMovie.create(movie.getId());
        boolean result = isCopyExist.isExistById(copy.getId());
        //then
        assertTrue(result);
        assertEquals(movie.getId(), copy.getMovie().getId());
        assertNull(copy.getUser());
        assertTrue(copy.isAvailable());
        assertNull(copy.getRentalDate());
        assertEquals(0, copy.getRentalDays());
    }

    @Test
    void shouldThrowExceptionWhenCreatingCopyOfAMovieThatDoesNotExist(){
        //given
        long idOfMovieThatDoNotExist = 5000;
        //then
        assertThrows(MovieDoesNotExistException.class, () ->
            createCopyOfAMovie.create(idOfMovieThatDoNotExist));

    }

}