package pl.VideoRental.useCase.port.moviePort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.sampleData.SampleDataStorage;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class IsMovieAvailableTest {

    @Autowired
    IsMovieAvailable isMovieAvailable;
    @Autowired
    GetMovieFromCatalog getMovieFromCatalog;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    CopyRepository copyRepository;

    @Test
    void shouldReturnTrueWhenMovieIsAvailable() throws MovieDoesNotExistException {
        //given
        String movieTitle = SampleDataStorage.MOVIE_1.getTitle();
        long movieId = getMovieFromCatalog.getByTitle(movieTitle).getId();
        //when
       boolean result = isMovieAvailable.isAvailable(movieId);
        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenMovieIsNotAvailable() throws MovieDoesNotExistException {
        //given
        String movieTitle = SampleDataStorage.MOVIE_3.getTitle();
        long movieId = getMovieFromCatalog.getByTitle(movieTitle).getId();
        Copy copy = getAllCopies.getAll().stream().filter(c -> c.getMovie().getId() == movieId).findFirst().orElse(null);
        copy.setAvailable(false);
        copyRepository.save(copy);
        //when
        boolean result = isMovieAvailable.isAvailable(movieId);
        //then
        assertFalse(result);
        copy.setAvailable(true);
        copyRepository.save(copy);
    }
}
