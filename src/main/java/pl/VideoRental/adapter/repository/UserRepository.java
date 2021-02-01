package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.VideoRental.domain.User;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository <User, Long> {

    public Optional<User> findByEmail(String email);
}
