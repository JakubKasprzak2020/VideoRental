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
import pl.VideoRental.useCase.port.moviePort.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(MovieController.class)
public class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private CreateMovie createMovie;
    @MockBean
    private DeleteMovie deleteMovie;
    @MockBean
    private GetAllMovies getAllMovies;
    @MockBean
    private GetMovieFromCatalog getMovieFromCatalog;
    @MockBean
    private UpdateMovie updateMovie;

    private final Movie movie1 = SampleDataStorage.MOVIE_1;
    private final Movie movie2 = SampleDataStorage.MOVIE_2;


    private final String movieJson = "{\n" +
            "        \"id\": 0,\n" +
            "        \"title\": \"A bridge too far\",\n" +
            "        \"description\": \"Year 1944, Europe...\",\n" +
            "        \"releaseDate\": \"1977-01-01\",\n" +
            "        \"genre\": \"HISTORICAL\"\n" +
            "    }";


    @Test
    void shouldGetAllMovies() throws Exception {
        //given
        List<Movie> movies = new ArrayList<>();
        movies.add(movie1);
        movies.add(movie2);
        String url = "/api/movies";
        //when
        Mockito.when(getAllMovies.getAll()).thenReturn(movies);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movies);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldGetMovie() throws Exception {
        //given
        String url = "/api/movies/" + movie1.getTitle();
        //when
        Mockito.when(getMovieFromCatalog.getByTitle(movie1.getTitle())).thenReturn(movie1);
        RequestBuilder request = MockMvcRequestBuilders.get(url);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isOk()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movie1);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldCreateNewMovie() throws Exception {
        //given
        Movie movie = Movie.builder()
                .title("A bridge too far")
                .genre(Genre.HISTORICAL)
                .description("Year 1944, Europe...")
                .releaseDate(LocalDate.of(1977, 1, 1))
                .build();
        String url = "/api/movies";
        //when
        Mockito.when(createMovie.createIfIsNotExisting(any(Movie.class))).thenReturn(movie);
        RequestBuilder request = MockMvcRequestBuilders
                .post(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson);
        //then
        MvcResult mvcResult = mockMvc.perform(request).andExpect(status().isCreated()).andReturn();
        String actualJsonResponse = mvcResult.getResponse().getContentAsString();
        String expectedJsonResponse = objectMapper.writeValueAsString(movie);
        assertEquals(expectedJsonResponse, actualJsonResponse);
    }

    @Test
    void shouldDeleteMovie() throws Exception {
        //given
        long id = 1;
        String url = "/api/movies/delete/" + id;
        //when
        Mockito.doNothing().when(deleteMovie).deleteById(id);
        RequestBuilder request = MockMvcRequestBuilders.delete(url);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(deleteMovie, times(1)).deleteById(id);
    }

//TODO status 415, not supported media type
    @Test
    void shouldUpdateMovie() throws Exception {
        //given
        long randomId = 1;
        String url = "/api/movies/update/" + randomId;
        //when
        Mockito.doNothing().when(updateMovie).update(eq(randomId), any(Movie.class));
        RequestBuilder request = MockMvcRequestBuilders
                .put(url)
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieJson);
        //then
        mockMvc.perform(request).andExpect(status().isOk());
        Mockito.verify(updateMovie, times(1))
                .update(eq(randomId), any(Movie.class));
    }


}
