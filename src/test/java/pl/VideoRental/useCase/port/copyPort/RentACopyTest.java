package pl.VideoRental.useCase.port.copyPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsNotRentedException;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class RentACopyTest {

    @Autowired
    RentACopy rentACopy;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    ReturnACopy returnACopy;

    @Test
    void shouldRentACopy() throws CopyIsNotRentedException {
        //given
        final int firstIndexValue = 0;
        User user = getAllUsers.getAll().get(firstIndexValue);
        Copy copy = getAllCopies.getAll().get(firstIndexValue);
        //when
        rentACopy.rent(copy, user);
        //then
        assertEquals(user.getId(), copy.getUser().getId());
        assertEquals(copy.getId(), user.getCopies().get(firstIndexValue).getId());
        returnACopy.returnACopy(copy);
    }

}