package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class MovieController {

    private final CreateMovie createMovie;
    private final DeleteMovie deleteMovie;
    private final GetAllMovies getAllMovies;
    private final GetMovieFromCatalog getMovieFromCatalog;
    private final UpdateMovie updateMovie;
    private final SortMovies sortMovies;


    @GetMapping(path="/api/movies")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAll() {
        return getAllMovies.getAllInAlphabeticalOrder();
    }

    @GetMapping(path="/api/movies/chrono")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAllInChronoOrder() {
        return getAllMovies.getAllInChronologicalOrder();
    }

    @GetMapping(path="/api/movies/genre/{genreName}")
    @ResponseStatus(HttpStatus.OK)
    public List<Movie> getAllWithGenre(@PathVariable String genreName){
        return sortMovies.sortByGenre(genreName);
    }

    @GetMapping("/api/movies/{title}")
    @ResponseStatus(HttpStatus.OK)
    public Movie get(@PathVariable String title) throws MovieDoesNotExistException {
        try {
            return getMovieFromCatalog.getByTitle(title);
        } catch (MovieDoesNotExistException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    @PostMapping("/admin/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public Movie create(@RequestBody Movie movie) throws MovieAlreadyExistException {
        return createMovie.createIfIsNotExisting(movie);
    }

    @DeleteMapping("admin/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void delete(@PathVariable long id) {
            deleteMovie.deleteById(id);
    }

    @PutMapping("admin/movies/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void update(@RequestBody Movie movie, @PathVariable long id) {
        updateMovie.update(id, movie);
    }




}
