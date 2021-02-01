package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.authentication.UserDetailsServiceImpl;
import pl.VideoRental.util.JsonConverter;
import pl.VideoRental.domain.User;
import pl.VideoRental.domain.UserSignInData;
import pl.VideoRental.useCase.port.userPort.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
    @MockBean
    private GetUserFromCatalog getUserFromCatalog;
    @MockBean
    private CreateUser createUser;
    @MockBean
    private DeleteUser deleteUser;
    @MockBean
    private UpdateUser updateUser;
    @MockBean
    private JsonConverter jsonConverter;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    private final User USER_1 = User.builder()
            .name("Winston")
            .lastName("Churchill")
            .email("win@gov.com")
            .address("Downing Street")
            .build();
    private final User USER_2 = User.builder()
            .name("Jozef")
            .lastName("Stalin")
            .email("jstalin@google.com")
            .address("Red Square")
            .build();

    private final String userJson = "{\n" +
            "        \"id\": 10,\n" +
            "        \"name\": \"Quentin\",\n" +
            "        \"lastName\": \"Tarantino\",\n" +
            "        \"password\": \"password\",\n" +
            "        \"email\": \"quentin@quentin.com\",\n" +
            "        \"address\": \"Hollywood\",\n" +
            "        \"userType\": \"REGULAR\"\n" +
            "    }";

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetAllUsers() throws Exception {
        //given
        List<User> users = new ArrayList<>();
        users.add(USER_1);
        users.add(USER_2);
        String url = "/admin/users";
        //when
        Mockito.when(getAllUsers.getAll()).thenReturn(users);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String expectedJsonResponse = objectMapper.writeValueAsString(users);
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldGetUser() throws Exception {
        //given
        long randomId = 7;
        String url = "/admin/users/" + randomId;
        //when
        Mockito.when(getUserFromCatalog.getById(randomId)).thenReturn(USER_1);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJson = objectMapper.writeValueAsString(USER_1);
        assertEquals(expectedJson, actualJsonResponse);
        Mockito.verify(getUserFromCatalog, times(1)).getById(randomId);
    }

    @Test
    void shouldCreateNewUser() throws Exception {
        //given
        UserSignInData userSignInData = UserSignInData.builder()
                .address(USER_1.getAddress())
                .email(USER_1.getEmail())
                .name(USER_1.getName())
                .lastName(USER_1.getLastName())
                .build();
        String url = "/registration";
        //when
        Mockito.when(createUser.create(any(UserSignInData.class))).thenReturn(USER_1);
        RequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userSignInData));
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(USER_1);
        assertEquals(expectedJsonResponse, actualJsonResponse);
        Mockito.verify(createUser, times(1)).create(any(UserSignInData.class));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldDeleteUser() throws Exception {
        //given
        long randomId = 3;
        String url = "/api/users/" + randomId;
        //when
        Mockito.doNothing().when(deleteUser).deleteById(any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteUser, times(1)).deleteById(any(Long.class));
    }


    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldUpdateUser() throws Exception {
        //given
        long randomId = 1;
        String url = "/api/users/" + randomId;
        //when
        Mockito.doNothing().when(updateUser).update(any(Long.class), any(User.class));
        Mockito.when(jsonConverter.getUserFromJson(userJson)).thenReturn(USER_1);
        RequestBuilder request = MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.TEXT_PLAIN)
                .content(userJson);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(updateUser, times(1))
                .update(any(Long.class), any(User.class));
    }



}