package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.DeleteMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final CreateMovie createMovie;
    private final DeleteMovie deleteMovie;
    private final GetAllMovies getAllMovies;
    private final GetMovieFromCatalog getMovieFromCatalog;


    @GetMapping(path="/api/movies")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAllMovies() {
        return getAllMovies.getAll();
    }

    @GetMapping("/api/movies/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Movie getMovie(@PathVariable String title) throws MovieDoesNotExistException {
        try {
            return getMovieFromCatalog.getByTitle(title);
        } catch (MovieDoesNotExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/api/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMovie(@RequestBody Movie movie) throws MovieAlreadyExistException {
        try {
            createMovie.create(movie);
            System.out.println(movie.getTitle() + " was created");
        } catch ( MovieAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }

    @DeleteMapping("api/movies/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovie(@PathVariable long id) {
            deleteMovie.deleteById(id);
    }





}
