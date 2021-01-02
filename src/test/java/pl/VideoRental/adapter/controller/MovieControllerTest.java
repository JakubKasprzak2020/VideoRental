package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.VideoRental.SampleData.SampleDataStorage;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.DeleteMovie;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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
    void shouldGetAllMovies() throws Exception {
        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        Mockito.when(getAllMovies.getAll()).thenReturn(movies);
        String url = "/api/movies";
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movies);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldGetMovie() throws Exception {
        Mockito.when(getMovieFromCatalog.getByTitle(movie1.getTitle())).thenReturn(movie1);
        String url = "/api/movies/" + movie1.getTitle();
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movie1);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldCreateNewMovie() throws Exception {
        Movie movie = Movie.builder()
                .title("A bridge too far")
                .genre(Genre.HISTORICAL)
                .description("Year 1944, Europe...")
                .releaseDate(LocalDate.of(1977, 1, 1))
                .build();

        String movieJson = "{\n" +
                "        \"id\": 0,\n" +
                "        \"title\": \"A bridge too far\",\n" +
                "        \"description\": \"Year 1944, Europe...\",\n" +
                "        \"releaseDate\": \"1977-01-01\",\n" +
                "        \"genre\": \"HISTORICAL\"\n" +
                "    }";

        Mockito.when(createMovie.createIfIsNotExisting(any(Movie.class))).thenReturn(movie);
        String url = "/api/movies";
        RequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson);
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movie);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldDeleteMovie() throws Exception {
        long id = 1;
        Mockito.doNothing().when(deleteMovie).deleteById(id);
        String url = "/api/movies/delete/" + id;
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteMovie, times(1)).deleteById(id);
    }





}
