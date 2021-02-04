package pl.VideoRental.useCase.port.movieRatingPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRatingRepository;
import pl.VideoRental.domain.MovieRating;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllMovieRatings {

    private final MovieRatingRepository movieRatingRepository;

    public List<MovieRating> getAll(){
        List <MovieRating> movieRatings = new ArrayList<>();
        movieRatingRepository.findAll().iterator().forEachRemaining(movieRatings :: add);
        return movieRatings;
    }


}
