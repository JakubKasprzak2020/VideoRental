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
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.DeleteMovie;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;


import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    private CreateMovie createMovie;
    @MockBean
    private DeleteMovie deleteMovie;
    @MockBean
    private GetAllMovies getAllMovies;
    @MockBean
    private GetMovieFromCatalog getMovieFromCatalog;

    Movie movie1 = SampleDataStorage.MOVIE_1;
    Movie movie2 = SampleDataStorage.MOVIE_2;

    @Test
    void shouldReturnMovie() throws Exception {
        Mockito.when(getMovieFromCatalog.getByTitle(movie1.getTitle())).thenReturn(movie1);
        String url = "/api/movies/" + movie1.getTitle();
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movie1);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

}
