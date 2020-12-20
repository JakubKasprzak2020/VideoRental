package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class IsCopyFreeTest {

    @Autowired
    IsCopyFree isCopyFree;

    public final Copy copy = new Copy();


    @Test
    void shouldReturnTrueWhenCopyIsFree() throws MovieDoesNotExistException {
        //when
       copy.setAvailable(true);
       boolean result = isCopyFree.isFree(copy);
        //then
        assertTrue(result);
    }

    @Test
    void shouldReturnFalseWhenCopyIsNotFree() throws MovieDoesNotExistException {
        //when
        copy.setAvailable(false);
        boolean result = isCopyFree.isFree(copy);
        //then
        assertFalse(result);
    }

}