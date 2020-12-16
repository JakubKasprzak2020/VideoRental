package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieAlreadyExistException;

@Component
@RequiredArgsConstructor
public class CreateMovie {

    private final MovieRepository movieRepository;
    private final IsMovieExist isMovieExist;

    public void create(Movie movie) throws MovieAlreadyExistException {
        if (!isMovieExist.isExistByTitle(movie.getTitle())) {
            movieRepository.save(movie);
        } else {
            throw new MovieAlreadyExistException(movie.getTitle());
        }
    }

}
