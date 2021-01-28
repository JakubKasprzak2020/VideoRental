package pl.VideoRental.useCase.port.userPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteUserTest {

    @Autowired
    private DeleteUser deleteUser;
    @Autowired
    private CreateUser createUser;
    @Autowired
    private GetUserFromCatalog getUserFromCatalog;
    @Autowired
    private GetAllUsers getAllUsers;
    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    private UserSignInData userSignInData = UserSignInData.builder()
            .name("Marie")
            .lastName("Curie")
            .email("mc@quentin.com")
            .password("password")
            .address("Paris")
            .build();

    @Test
    void shouldDeleteUserById() {
        //given
        User user = createUser.create(userSignInData);
        //when
        deleteUser.deleteById(user.getId());
        //then
        assertThrows(UserDoesNotExistException.class, () -> getUserFromCatalog.getById(user.getId()));
        assertNull(applicationUserRepository.findByUsername(user.getEmail()));
    }

    @Test
    void shouldNotDeleteAnythingWithWrongIdAsAnArgument() {
        //given
        int usersSizeBeforeDeleting = getAllUsers.getAll().size();
        long userIdThatNotExist = 789;
        //when
        deleteUser.deleteById(userIdThatNotExist);
        int usersSizeAfterDeleting = getAllUsers.getAll().size();
        //then
        assertEquals(usersSizeBeforeDeleting, usersSizeAfterDeleting);

    }

}