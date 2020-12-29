package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

@Component
@RequiredArgsConstructor
public class GetMovieFromCatalog {

    private final MovieRepository movieRepository;

    public Movie getById(long id) throws MovieDoesNotExistException {
        return movieRepository.findById(id).orElseThrow(() -> new MovieDoesNotExistException("Movie with id " + id));
    }

    public Movie getByTitle(String title) throws MovieDoesNotExistException {
        return movieRepository.findByTitle(title).orElseThrow(() -> new MovieDoesNotExistException(title));
    }

}
