package pl.VideoRental.useCase.port.moviePort;

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

    public Movie create(Movie movie) throws MovieAlreadyExistException {
        if (!isMovieExist.isExistByTitle(movie.getTitle())) {
            movieRepository.save(movie);
            return movie;
        } else {
            throw new MovieAlreadyExistException(movie.getTitle());
        }
    }


    public Movie createIfIsNotExisting(Movie movie) {
        try {
            String title = movie.getTitle();
            System.out.println(String.format("Movie %s was created successfully", title));
            return create(movie);
        } catch (MovieAlreadyExistException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

}
