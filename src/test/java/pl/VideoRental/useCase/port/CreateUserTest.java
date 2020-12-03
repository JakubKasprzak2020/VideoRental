package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExist;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUserTest {

    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetUserFromCatalog getUserFromCatalog;

    @Test
    void create_user() throws UserDoesNotExist {
        //given
        String name = "John";
        String lastName = "Smith";
        String password = "myPassword1";
        long expectedValueofIdOfUser = 1; //with generatedValue Strategy = Auto (user id = 1, cart id = 2)
        long expectedValueofIdOfCart = 2; //with generatedValue Strategy = Auto (user id = 1, cart id = 2)
        //when
        createUser.create(name, lastName, password);
        User user = getUserFromCatalog.getById(expectedValueofIdOfUser);
        //then
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(expectedValueofIdOfCart, user.getCart().getId());
    }

}