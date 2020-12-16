package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

@Component
@RequiredArgsConstructor
public class CreateCopyOfAMovie {

    private final GetMovieFromCatalog getMovieFromCatalog;
    private final CopyRepository copyRepository;
    private final MovieRepository movieRepository;
    private final IsMovieExist isMovieExist;

    @Transactional //TODO - Lazy initialization error without this annotation
    public void create(long id) throws MovieDoesNotExistException {
        if (!isMovieExist.isExistById(id)) {
            throw new MovieDoesNotExistException("Movie with id " + id);
        }
        Movie movie = getMovieFromCatalog.getById(id);
        Copy copy = createCopy(movie);
        copyRepository.save(copy);
        movie.getCopies().add(copy);
        movieRepository.save(movie);
    }

    private Copy createCopy(Movie movie) {
        return Copy.builder()
                .movie(movie)
                .isAvailable(true)
                .build();
    }


}
