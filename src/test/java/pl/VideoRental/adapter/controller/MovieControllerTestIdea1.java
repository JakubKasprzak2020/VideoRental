package pl.VideoRental.adapter.controller;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.web.reactive.server.WebTestClient;
import pl.VideoRental.domain.Genre;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.DeleteMovie;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MovieControllerTestIdea1 {

    @Test
            void shouldReturnAllMovies() {

        List<Movie> movies = new ArrayList<>();

        Movie movie1 = Movie.builder()
                .title("Inglorious Bastards")
                .genre(Genre.ACTION)
                .description("Second World War in Europe...")
                .releaseDate(LocalDate.of(2009, 1, 1))
                .build();

        Movie movie2 = Movie.builder()
                .title("E.T.")
                .genre(Genre.SCI_FI)
                .releaseDate(LocalDate.of(1982, 1, 1))
                .description("An Alien appears on the Earth...")
                .build();

        movies.add(movie1);
        movies.add(movie2);

        CreateMovie createMovie = Mockito.mock(CreateMovie.class);
        GetAllMovies getAllMovies = Mockito.mock(GetAllMovies.class);
        DeleteMovie deleteMovie = Mockito.mock(DeleteMovie.class);
        GetMovieFromCatalog getMovieFromCatalog = Mockito.mock(GetMovieFromCatalog.class);


        when(getAllMovies.getAll()).thenReturn(movies);

        WebTestClient testClient = WebTestClient.bindToController(new MovieController(createMovie, deleteMovie, getAllMovies, getMovieFromCatalog))
                .build();

        testClient.get().uri("/api/movies")
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$").isNotEmpty();
    }
}