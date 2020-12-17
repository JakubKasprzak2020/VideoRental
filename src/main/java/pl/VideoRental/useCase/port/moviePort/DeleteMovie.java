package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteMovie {

    /**
     * If movie is deleted all copies of this movie are removed
     * becouse of annotation CascadeType and OrphanRemoval in Movie class
     */

    private final MovieRepository movieRepository;
    private final GetMovieFromCatalog getMovieFromCatalog;


    public void deleteById(long id){
        try {
            getMovieFromCatalog.getById(id);
            movieRepository.deleteById(id);
        } catch (MovieDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }


}
