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
public class AddMovieToCatalog {

    private final MovieRepository movieRepository;
    private final IsMovieExist isMovieExist;

    public void add(Movie movie) throws MovieAlreadyExistException {
        if (!isMovieExist.isExistByTitle(movie.getTitle())) {
            movieRepository.save(movie);
        } else {
            throw new MovieAlreadyExistException(movie.getTitle());
        }
    }

}
