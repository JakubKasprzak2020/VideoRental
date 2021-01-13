package pl.VideoRental.useCase.port.userPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.sampleData.SampleDataInit;
import pl.VideoRental.domain.User;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllUsersTest {

    @Autowired
    private GetAllUsers getAllUsers;

    @Test
            public void shouldGetAllUsers() {
        //given
        int usersSize = SampleDataInit.usersLengthMarker;
        //when
        List<User> users = getAllUsers.getAll();
        //then
        assertNotNull(users);
        assertEquals(usersSize, users.size());
    }
}