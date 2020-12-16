package pl.VideoRental.useCase.port.userPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUserTest {

    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetUserFromCatalog getUserFromCatalog;

    @Test
    void create_user() throws UserDoesNotExistException {
        //given
        String name = "John";
        String lastName = "Smith";
        String password = "myPassword1";
        String email = "email@email.com";
        String address = "address";
        long expectedValueofIdOfUser = 2; //with generatedValue Strategy = Auto (user id = 2, cart id = 1)
        long expectedValueofIdOfCart = 1; //with generatedValue Strategy = Auto (user id = 2, cart id = 1)
        //when
        createUser.create(name, lastName, password, email, address);
        User user = getUserFromCatalog.getById(expectedValueofIdOfUser);
        //then
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(expectedValueofIdOfCart, user.getCart().getId());
    }

}