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
        long expectedValueofIdOfUser = 1; //with generatedValue Strategy = Auto (user id = 1, cart id = 2)
        //when
        createUser.create(name, lastName, password);
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