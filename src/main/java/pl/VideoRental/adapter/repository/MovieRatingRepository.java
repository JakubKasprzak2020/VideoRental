package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.VideoRental.domain.MovieRating;

@Repository
public interface MovieRatingRepository extends CrudRepository<MovieRating, Long> {
}
