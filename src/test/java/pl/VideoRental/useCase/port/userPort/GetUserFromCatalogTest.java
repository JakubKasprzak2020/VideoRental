package pl.VideoRental.useCase.port.userPort;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.sampleData.SampleDataStorage;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetUserFromCatalogTest {


    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetUserFromCatalog getUserFromCatalog;
    @Autowired
    private GetAllUsers getAllUsers;
    @Autowired
    private DeleteUser deleteUser;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    void shouldGetUserWhenUserExist() throws UserDoesNotExistException {
        //given
        String name = "John";
        String lastName = "Smith";
        String password = "myPassword1";
        String email = "email@email.com";
        String address = "address";
        //when
        UserSignInData userSignInData = UserSignInData.builder()
                .name(name)
                .lastName(lastName)
                .password(password)
                .email(email)
                .address(address)
                .build();
       User user = createUser.create(userSignInData);
        User userFromCatalog = getUserFromCatalog.getById(user.getId());
        //then
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
        assertEquals(address, user.getAddress());
        assertEquals(user.getId(), userFromCatalog.getId());
        deleteUser.deleteById(user.getId());
    }

    @Test
    void shouldThrowExceptionWhenUserDoesNotExist() throws UserDoesNotExistException {
        //given
        long numberOfUserIdThatNotExist = 1300;
        //then
        assertThrows(UserDoesNotExistException.class, ()->{getUserFromCatalog.getById(numberOfUserIdThatNotExist);});
    }


    @Test
    void shouldFindUserByApplicationUser() {
        //given
        String username = SampleDataStorage.USER_SIGN_IN_DATA_1.getEmail();
        String name = SampleDataStorage.USER_SIGN_IN_DATA_1.getName();
        String lastName = SampleDataStorage.USER_SIGN_IN_DATA_1.getLastName();
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        //when
        User user = getUserFromCatalog.getByApplicationUser(applicationUser);
        //then
        assertNotNull(user);
        assertEquals(username, user.getEmail());
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
    }

}