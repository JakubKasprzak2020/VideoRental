package pl.VideoRental.useCase.port;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.UserDoesNotExist;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class GetUserFromCatalogTest {


    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetUserFromCatalog getUserFromCatalog;

    @Test
    void getUserWhenUserExist() throws UserDoesNotExist {
        //given
        String name = "John";
        String lastName = "Smith";
        String password = "myPassword1";
        String email = "email@email.com";
        String address = "address";
        long expectedValueofIdOfUser = 2; //with generatedValue Strategy = Auto (user id = 2, cart id = 1)
        //when
        createUser.create(name, lastName, password, email, address);
        User user = getUserFromCatalog.getById(expectedValueofIdOfUser);
        //then
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
    }

    @Test
    void getUserWhenUserDoesNotExist() throws UserDoesNotExist {
        //given
        long randomNumber = 1;
        //then
        assertThrows(UserDoesNotExist.class, ()->{getUserFromCatalog.getById(randomNumber);});
    }

}