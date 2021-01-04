package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

@Component
@RequiredArgsConstructor
public class UpdateMovie {

    private final
    GetMovieFromCatalog getMovieFromCatalog;
    private final MovieRepository movieRepository;

    public void update(long id, Movie newMovie) {
        try {
           Movie oldMovie = getMovieFromCatalog.getById(id);
           oldMovie.setDescription(newMovie.getDescription());
           oldMovie.setGenre(newMovie.getGenre());
           oldMovie.setReleaseDate(newMovie.getReleaseDate());
           oldMovie.setTitle(newMovie.getTitle());
           movieRepository.save(oldMovie);
        } catch (MovieDoesNotExistException exception){
            System.out.println(exception.getMessage());
        }
    }


}
