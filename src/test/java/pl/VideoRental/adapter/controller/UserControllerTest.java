package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.SampleData.SampleDataStorage;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.userPort.CreateUser;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private GetAllUsers getAllUsers;

    @Autowired
    private CreateUser createUser;

/*    private User user1 = createUser.create(SampleDataStorage.USER_SIGN_IN_DATA_1);
    private User user2 = createUser.create(SampleDataStorage.USER_SIGN_IN_DATA_2);*/

    @Test
    void shouldGetAllUsers() throws Exception {
       /* User user1 = createUser.create(SampleDataStorage.USER_SIGN_IN_DATA_1);
        User user2 = createUser.create(SampleDataStorage.USER_SIGN_IN_DATA_2);*/
        List<User> users = new ArrayList<>();
/*        users.add(user1);
        users.add(user2);*/
        Mockito.when(getAllUsers.getAll()).thenReturn(users);
        String url = "/api/users";
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String expectedJsonResponse = objectMapper.writeValueAsString(users);
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

}