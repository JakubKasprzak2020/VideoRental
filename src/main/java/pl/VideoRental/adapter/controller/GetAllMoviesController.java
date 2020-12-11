package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.port.getAllUtils.GetAllMovies;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GetAllMoviesController {

    private final GetAllMovies getAllMovies;


    @GetMapping(path="/api/movies")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAllMovies() {
        return getAllMovies.getAll();
    }

}
