package pl.VideoRental.useCase.port.userPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.sampleData.SampleDataStorage;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateUserTest {

    @Autowired
    UpdateUser updateUser;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetUserFromCatalog getUserFromCatalog;

    UserSignInData userSignInData = SampleDataStorage.USER_SIGN_IN_DATA_1;

    User user = User.builder()
            .password(userSignInData.getPassword())
            .address(userSignInData.getAddress())
            .email(userSignInData.getEmail())
            .lastName("Eastwood")
            .name("Clint")
            .build();

    @Test
    void shouldUpdateUser() throws UserDoesNotExistException {
        //given
        int firstIndexNumber = 0;
        long userId = getAllUsers.getAll().get(firstIndexNumber).getId();
        //when
        updateUser.update(userId, user);
        //then
        User updatedUser = getUserFromCatalog.getById(userId);
        assertEquals(user.getAddress(), updatedUser.getAddress());
        assertEquals(user.getName(), updatedUser.getName());
        assertEquals(user.getLastName(), updatedUser.getLastName());
        assertEquals(user.getPassword(), updatedUser.getPassword());
        assertEquals(user.getEmail(), updatedUser.getEmail());
        user.setName(SampleDataStorage.USER_SIGN_IN_DATA_1.getName());
        user.setLastName(SampleDataStorage.USER_SIGN_IN_DATA_1.getLastName());
        updateUser.update(userId, user);
    }

    @Test
    void shouldNotUpdateUserWhenIdIsIncorrect() {
        //given
        long idOfUserThatDoesNotExist = 1000;
        //when
        updateUser.update(idOfUserThatDoesNotExist, user);
        //then
        List<User> listOfUsersWhoseNameIsClint = getAllUsers.getAll().stream()
                .filter(u -> u.getName().equals(user.getName())).collect(Collectors.toList());
      assertTrue(listOfUsersWhoseNameIsClint.isEmpty());
    }
}