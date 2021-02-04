package pl.VideoRental.useCase.port.movieRatingPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.MovieRatingRepository;

@Component
@RequiredArgsConstructor
public class DeleteMovieRating {

    private final MovieRatingRepository movieRatingRepository;

    public void delete(long id){
        movieRatingRepository.deleteById(id);
    }
}
