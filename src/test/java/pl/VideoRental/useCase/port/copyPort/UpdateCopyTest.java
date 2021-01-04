package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.SampleData.SampleDataStorage;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateCopyTest {

    @Autowired
    UpdateCopy updateCopy;
    @Autowired
    CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    GetCopyFromCatalog getCopyFromCatalog;
    @Autowired
    DeleteCopy deleteCopy;


    @Test
    void shouldUpdateCopy() throws MovieDoesNotExistException, CopyDoesNotExistException {
        //given
        Copy oldCopy = createCopyOfAMovie.create(SampleDataStorage.MOVIE_1.getId());
        Copy newCopy = Copy.builder()
                .isAvailable(oldCopy.isAvailable())
                .movie(SampleDataStorage.MOVIE_2)
                .rentalDate(oldCopy.getRentalDate())
                .user(oldCopy.getUser())
                .rentalDays(oldCopy.getRentalDays())
                .build();
        //when
        updateCopy.update(oldCopy.getId(), newCopy);
        //then
        assertEquals(SampleDataStorage.MOVIE_2.getId(), getCopyFromCatalog.get(oldCopy.getId()).getMovie().getId());
        deleteCopy.deleteById(oldCopy.getId());
    }

    @Test
    void shouldNotUpdateMovieWhenIdIsIncorrect() throws MovieDoesNotExistException, CopyDoesNotExistException {
        //given
        Copy oldCopy = createCopyOfAMovie.create(SampleDataStorage.MOVIE_1.getId());
        Copy newCopy = Copy.builder()
                .isAvailable(oldCopy.isAvailable())
                .movie(SampleDataStorage.MOVIE_2)
                .rentalDate(oldCopy.getRentalDate())
                .user(oldCopy.getUser())
                .rentalDays(oldCopy.getRentalDays())
                .build();
        long idOfCopyThatDoesNotExist = 560;
        //when
        updateCopy.update(idOfCopyThatDoesNotExist, newCopy);
        //then
        assertEquals(SampleDataStorage.MOVIE_1.getId(), getCopyFromCatalog.get(oldCopy.getId()).getMovie().getId());
    }

}