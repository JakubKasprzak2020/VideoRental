package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.VideoRental.domain.Movie;

import java.util.Optional;

@Repository
public interface MovieRepository extends CrudRepository<Movie, Long> {

    public Optional<Movie> findByTitle(String title);

    public boolean existsByTitle(String title);

    public Long deleteByTitle(String title);

}
