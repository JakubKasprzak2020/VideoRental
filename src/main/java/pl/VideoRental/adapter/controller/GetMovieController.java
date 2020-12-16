package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.GetMovieFromCatalog;

@RestController
@RequiredArgsConstructor
public class GetMovieController {

    private final GetMovieFromCatalog getMovieFromCatalog;

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



}
