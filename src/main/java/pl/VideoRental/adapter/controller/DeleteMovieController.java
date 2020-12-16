package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.DeleteMovieFromCatalog;

@RestController
@RequiredArgsConstructor
public class DeleteMovieController {

    private final DeleteMovieFromCatalog deleteMovieFromCatalog;

    @DeleteMapping("api/movies/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteMovie(@PathVariable long id) throws MovieDoesNotExistException {
        try {
            deleteMovieFromCatalog.deleteById(id);
        } catch (MovieDoesNotExistException e) {
            System.out.println(e.getMessage());
        }
    }





}

