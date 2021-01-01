package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteCopyTest {

    @Autowired
    DeleteCopy deleteCopy;
    @Autowired
    CreateCopyOfAMovie createCopyOfAMovie;
    @Autowired
    GetAllMovies getAllMovies;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    private IsCopyExist isCopyExist;

    private Copy createSampleCopy() throws MovieDoesNotExistException {
        return createCopyOfAMovie.create(getAllMovies.getAll().get(0).getId());
    }

    @Test
    void shouldDeleteCopyById() throws MovieDoesNotExistException {
        //given
        Copy copy = createSampleCopy();
        //when
        deleteCopy.deleteById(copy.getId());
        //then
        assertFalse(isCopyExist.isExistById(copy.getId()));
    }

    @Test
    void shouldNotDeleteAnythingWithWrongIdAsAnArgument() {
        //given
        int copiesSizeBeforeDeleting = getAllCopies.getAll().size();
        long copyIdThatNotExist = 1300;
        //when
        deleteCopy.deleteById(copyIdThatNotExist);
        int copiesSizeAfterDeleting = getAllCopies.getAll().size();
        //then
        assertEquals(copiesSizeBeforeDeleting, copiesSizeAfterDeleting);
    }
}