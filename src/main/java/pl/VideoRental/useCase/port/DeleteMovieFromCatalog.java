package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRepository;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExist;

@Component
@RequiredArgsConstructor
public class DeleteMovieFromCatalog {

    private final MovieRepository movieRepository;

    private final GetMovieFromCatalog getMovieFromCatalog;

    public void delete(Movie movie) throws MovieDoesNotExist {
            getMovieFromCatalog.getById(movie.getId());
            movieRepository.delete(movie);
    }


    //TODO temporarily not working
    public void deleteByTitle(String title){
        movieRepository.deleteByTitle(title);
    }

    public void deleteById(long id) throws MovieDoesNotExist {
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
