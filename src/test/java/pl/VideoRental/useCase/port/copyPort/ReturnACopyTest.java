package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsNotRentedException;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
//@DirtiesContext(methodMode = DirtiesContext.MethodMode.AFTER_METHOD)
class ReturnACopyTest {

    @Autowired
    RentACopy rentACopy;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    ReturnACopy returnACopy;


    @Test
    void shouldReturnACopyWhenCopyWasRented() throws CopyIsNotRentedException {
        //given
        final int firstIndexValue = 0;
        User user = getAllUsers.getAll().get(firstIndexValue);
        Copy copy = getAllCopies.getAll().get(firstIndexValue);
        //when
        rentACopy.rent(copy, user);
        returnACopy.returnACopy(copy);
        //then
        assertTrue(copy.isAvailable());
        assertEquals(0, copy.getRentalDays());
        assertNull(copy.getRentalDate());
        assertNull(copy.getUser());
        assertEquals(0, user.getCopies().size());
    }

    @Test
    void shouldReturnOneCopyWhenTwoWereRented() throws CopyIsNotRentedException {
        //given
        final int firstIndexValue = 0;
        final int secondIndexValue = 1;
        User user = getAllUsers.getAll().get(secondIndexValue);
        Copy copy1 = getAllCopies.getAll().get(firstIndexValue);
        Copy copy2 = getAllCopies.getAll().get(secondIndexValue);
        //when
        rentACopy.rent(copy1, user);
        rentACopy.rent(copy2, user);
        returnACopy.returnACopy(copy1);
        //then
        assertEquals(1, user.getCopies().size()); // expected 1 because two copies had been rented and one was returned
        returnACopy.returnACopy(copy2);
    }

    @Test
    void shouldThrowAnExceptionWhenCopyWasNotRented(){
        //given
        final int firstIndexValue = 0;
        Copy copy1 = getAllCopies.getAll().get(firstIndexValue);
        //then
        assertThrows(CopyIsNotRentedException.class, ()-> returnACopy.returnACopy(copy1));

    }

}