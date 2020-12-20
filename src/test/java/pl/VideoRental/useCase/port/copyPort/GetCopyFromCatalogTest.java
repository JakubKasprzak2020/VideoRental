package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetCopyFromCatalogTest {

    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    GetCopyFromCatalog getCopyFromCatalog;

    @Test
    void shouldGetCopyFromCatalog() throws CopyDoesNotExistException {
        //given
        Copy copy = getAllCopies.getAll().get(0);
        //when
        Copy testedCopy = getCopyFromCatalog.get(copy.getId());
        //then
        assertEquals(copy.getId(), testedCopy.getId());
        assertEquals(copy.isAvailable(), testedCopy.isAvailable());
        assertEquals(copy.getUser(), testedCopy.getUser());
        assertEquals(copy.getRentalDate(), testedCopy.getRentalDate());
        assertEquals(copy.getRentalDays(), testedCopy.getRentalDays());
        assertEquals(copy.getMovie().getId(), testedCopy.getMovie().getId());
    }

    @Test
    void shouldThrowExceptionWhenGettingCopyThatDoesNotExist()  {
        //given
        long idOfCopyThatDoesNotExist = 4200;
        //then
        assertThrows(CopyDoesNotExistException.class, ()->  getCopyFromCatalog.get(idOfCopyThatDoesNotExist));
    }
}