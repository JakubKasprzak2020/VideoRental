package pl.VideoRental.authentication;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ApplicationUserRepository extends CrudRepository<ApplicationUser, Long> {

    public ApplicationUser findByUsername(String username);
}
