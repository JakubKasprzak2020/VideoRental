package pl.VideoRental.useCase.port.userPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.authentication.ApplicationUserRole;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CreateUserTest {

    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetUserFromCatalog getUserFromCatalog;
    @Autowired
    private DeleteUser deleteUser;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Test
    void shouldCreateUser() throws UserDoesNotExistException {
        //given
        String name = "John";
        String lastName = "Smith";
        String password = "myPassword1";
        String email = "email@email.com";
        String address = "address";
        int expectedNumberOfRoles = 1;
        String expectedNameOfRole = ApplicationUserRole.USER.getName();
        //when
        UserSignInData userSignInData = UserSignInData.builder()
                .name(name)
                .lastName(lastName)
                .password(password)
                .email(email)
                .address(address)
                .build();
        User user = createUser.create(userSignInData);
        //then
        assertEquals(name, user.getName());
        assertEquals(lastName, user.getLastName());
        assertEquals(password, user.getPassword());
        assertEquals(email, user.getEmail());
        assertEquals(address, user.getAddress());
        assertTrue(user.getId() != 0);
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(user.getEmail());
        assertNotNull(applicationUser);
        assertEquals(user.getEmail(), applicationUser.getUsername());
        assertEquals(expectedNumberOfRoles, applicationUser.getRoles().size());
        assertEquals(expectedNameOfRole, applicationUser.getRoles().get(0));
        deleteUser.deleteById(user.getId());
    }

}