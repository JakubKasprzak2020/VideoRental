package pl.VideoRental.useCase.port.movieRatingPort;

import com.google.gson.internal.LinkedTreeMap;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Movie;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetAllMovies;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

@Component
@AllArgsConstructor
public class GetAllAverageRatings {

    private final GetAllMovies getAllMovies;
    private final CountAverageRating countAverageRating;

    public Map<String, Double> getAll() throws MovieDoesNotExistException {
        List<Movie> movies = getAllMovies.getAll();
        Map <String, Double> result = new TreeMap<>();
        for (Movie movie : movies){
            result.put(movie.getTitle(), countAverageRating.count(movie.getId()));
        }
        return result;
    }


}
