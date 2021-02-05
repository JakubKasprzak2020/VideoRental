package pl.VideoRental.useCase.port.movieRatingPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.MovieRating;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;

import java.util.List;

@Component
@RequiredArgsConstructor
public class CountAverageRating {

    private final GetMovieFromCatalog getMovieFromCatalog;
    private final GetAllMovieRatings getAllMovieRatings;


    public double count(long movieId) throws MovieDoesNotExistException {
        List<MovieRating> movieRatings = filterMovieRatings(movieId);
        double sum = sumRatings(movieRatings);
        return (double) sum / movieRatings.size();
    }

    private List<MovieRating> filterMovieRatings(long movieId) throws MovieDoesNotExistException {
        getMovieFromCatalog.getById(movieId);
        List<MovieRating> movieRatings = getAllMovieRatings.getAll();
        movieRatings.removeIf(m -> m.getMovieId() != movieId);
        return movieRatings;
    }

    private double sumRatings(List<MovieRating> movieRatings){
        int sum = 0;
        for (MovieRating m : movieRatings) {
            sum += m.getRating();
        }
        return sum;
    }


}
