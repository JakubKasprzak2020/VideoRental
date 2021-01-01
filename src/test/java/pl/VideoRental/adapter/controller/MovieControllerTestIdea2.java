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
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.DeleteMovie;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTestIdea2 {

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


    @Test
    void shouldReturnMovie() throws Exception {

        Movie movie = Movie.builder()
                .title("E.T.")
                .genre(Genre.SCI_FI)
                .releaseDate(LocalDate.of(1982, 1, 1))
                .description("An Alien appears on the Earth...")
                .build();

      //  GetMovieFromCatalog getMovieFromCatalog = Mockito.mock(GetMovieFromCatalog.class);
        Mockito.when(getMovieFromCatalog.getByTitle("E.T.")).thenReturn(movie);

        String url = "/api/movies/E.T.";



        RequestBuilder request = MockMvcRequestBuilders.get(url);
       // MvcResult result = mockMvc.perform(request).andReturn();
        // assertEquals("E.T", result.getResponse().getContentAsString());
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();

        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movie);

        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

}
