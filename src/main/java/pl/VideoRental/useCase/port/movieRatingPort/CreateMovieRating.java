package pl.VideoRental.useCase.port.movieRatingPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRatingRepository;
import pl.VideoRental.domain.MovieRating;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.MovieDoesNotExistException;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;

@Component
@RequiredArgsConstructor
public class CreateMovieRating {

    private final MovieRatingRepository movieRatingRepository;
    private final GetMovieFromCatalog getMovieFromCatalog;

    public MovieRating create(User user,long movieId, int rating) throws MovieDoesNotExistException {
        getMovieFromCatalog.getById(movieId);
        if (rating < MovieRating.MIN_RATING || rating > MovieRating.MAX_RATING){
            throw new IllegalArgumentException("Movie Rating should be between 1 and 10");
        }
        MovieRating movieRating = MovieRating.builder()
                .userId(user.getId())
                .movieId(movieId)
                .rating(rating)
                .build();
        movieRatingRepository.save(movieRating);
        return movieRating;
    }


}
