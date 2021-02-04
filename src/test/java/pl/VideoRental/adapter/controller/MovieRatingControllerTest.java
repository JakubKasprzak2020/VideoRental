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
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.port.movieRatingPort.CreateMovieRating;
import pl.VideoRental.useCase.port.movieRatingPort.DeleteMovieRating;
import pl.VideoRental.useCase.port.movieRatingPort.GetAllMovieRatings;
import pl.VideoRental.useCase.port.userPort.GetUserFromCatalog;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieRatingController.class)
class MovieRatingControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateMovieRating createMovieRating;
    @MockBean
    private DeleteMovieRating deleteMovieRating;
    @MockBean
    private GetAllMovieRatings getAllMovieRatings;
    @MockBean
    private GetUserFromCatalog getUserFromCatalog;
    @MockBean
    private UserDetailsServiceImpl userDetailsService;

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldCreateMovieRating() throws Exception {
        //given
        String url = "/api/ratings/77";
        String rating = "7";
        User user = User.builder().build();
        //when
        Mockito.when(getUserFromCatalog.getByEmail(any(String.class))).thenReturn(user);
        Mockito.when(createMovieRating.create(any(User.class), any(Long.class), any(Integer.class))).thenReturn(new MovieRating());
        RequestBuilder request = MockMvcRequestBuilders.post(url)
                .contentType(MediaType.TEXT_PLAIN)
                        .content(rating);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(getUserFromCatalog, times(1)).getByEmail(any(String.class));
        Mockito.verify(createMovieRating, times(1)).create(any(User.class), any(Long.class), any(Integer.class));
    }

    @Test
    @WithMockUser(username = "user", password = "user", roles = "USER")
    void shouldGetAllMovieRatings() throws Exception {
        //given
        String url = "/api/ratings";
        //when
        Mockito.when(getAllMovieRatings.getAll()).thenReturn(new ArrayList<>());
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        Mockito.verify(getAllMovieRatings, times(1)).getAll();
    }

    @Test
    @WithMockUser(username = "admin", password = "admin", roles = "ADMIN")
    void shouldDeleteMovieRatings() throws Exception {
        //given
        long randomId = 16;
        String url = "/admin/ratings/" + randomId;
        //when
        Mockito.doNothing().when(deleteMovieRating).delete(Mockito.any(Long.class));
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteMovieRating, Mockito.times(1)).delete(Mockito.any(Long.class));
    }

}