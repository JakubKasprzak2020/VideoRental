package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.moviePort.IsMovieExist;

@Component
@RequiredArgsConstructor
public class CreateCopyOfAMovie {

    private final GetMovieFromCatalog getMovieFromCatalog;
    private final CopyRepository copyRepository;
    private final MovieRepository movieRepository;
    private final IsMovieExist isMovieExist;

    @Transactional //Lazy initialization error without this annotation
    public Copy create(long movieId) throws MovieDoesNotExistException {
        if (!isMovieExist.isExistById(movieId)) {
            throw new MovieDoesNotExistException("Movie with id " + movieId);
        }
        Movie movie = getMovieFromCatalog.getById(movieId);
        Copy copy = createCopy(movie);
        copyRepository.save(copy);
        movie.getCopies().add(copy);
        movieRepository.save(movie);
        return copy;
    }

    private Copy createCopy(Movie movie) {
        return Copy.builder()
                .movie(movie)
                .isAvailable(true)
                .build();
    }


}
