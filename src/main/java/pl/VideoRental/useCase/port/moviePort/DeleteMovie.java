package pl.VideoRental.useCase.port.moviePort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteMovie {

/*
If movie is deleted all copies are removed by annotation CascadeType and OrphanRemoval in Movie class
 */

    private final MovieRepository movieRepository;
    private final GetMovieFromCatalog getMovieFromCatalog;

    public void delete(Movie movie) throws MovieDoesNotExistException {
            getMovieFromCatalog.getById(movie.getId());
            movieRepository.delete(movie);
    }


    //TODO temporarily not working
    public void deleteByTitle(String title){
        movieRepository.deleteByTitle(title);
    }

    public void deleteById(long id) throws MovieDoesNotExistException {
            getMovieFromCatalog.getById(id);
            movieRepository.deleteById(id);
        }

    //METHOD WITH TRY CATCH BLOCK
/*    public void deleteById(long id){
        try {
            getMovieFromCatalog.getById(id);
            movieRepository.deleteById(id);
        } catch (MovieDoesNotExist exception) {
            System.out.println(exception.getMessage());
        }
    }*/





}
