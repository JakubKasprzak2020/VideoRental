package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

@Component
@RequiredArgsConstructor
public class GetMovieFromCatalog {

    private final MovieRepository movieRepository;

    public Movie getById(Long id) throws MovieDoesNotExist {
        return movieRepository.findById(id).orElseThrow(() -> new MovieDoesNotExist("Film o id " + id));
    }

    public Movie getByTitle(String title) throws MovieDoesNotExist {
        return movieRepository.findByTitle(title).orElseThrow(() -> new MovieDoesNotExist(title));
    }

}
