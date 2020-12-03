package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.VideoRental.domain.Copy;


@Repository
public interface CopyRepository extends CrudRepository<Copy, Long> {
}
