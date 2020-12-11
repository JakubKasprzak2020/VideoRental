package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.port.AddMovieToCatalog;

@RestController
@RequiredArgsConstructor
public class CreateMovieController {

    private final AddMovieToCatalog addMovieToCatalog;

    @PostMapping("/api/movies")
    @ResponseStatus(HttpStatus.CREATED)
    public void createMovie(@RequestBody Movie movie) throws MovieAlreadyExistException {
        try {
            addMovieToCatalog.add(movie);
            System.out.println(movie.getTitle() + " was created");
        } catch ( MovieAlreadyExistException e) {
            System.out.println(e.getMessage());
        }
    }


}
