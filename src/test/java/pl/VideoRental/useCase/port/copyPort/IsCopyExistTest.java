package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IsCopyExistTest {

    @Autowired
    CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    GetAllMovies getAllMovies;
    @Autowired
    IsCopyExist isCopyExist;
    @Autowired
    DeleteCopy deleteCopy;

    @Test
    void shouldReturnTrueWhenCopyExists() throws MovieDoesNotExistException {
        //given
        long idOfAMovie = getAllMovies.getAll().get(0).getId();
        Copy copy = createCopyOfAMovie.create(idOfAMovie);
        //when
        boolean result = isCopyExist.isExistById(copy.getId());
        //then
        assertTrue(result);
        deleteCopy.deleteById(copy.getId());
    }

    @Test
    void shouldReturnFalseWhenCopyDoesNotExist(){
        //given
        long idOfCopyThatDoesNotExist = 11000;
        //when
        boolean result = isCopyExist.isExistById(idOfCopyThatDoesNotExist);
        //then
        assertFalse(result);
    }

}