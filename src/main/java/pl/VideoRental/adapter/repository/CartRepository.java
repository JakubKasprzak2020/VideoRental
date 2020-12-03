package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.VideoRental.domain.Cart;

@Repository
public interface CartRepository extends CrudRepository<Cart, Long> {
}
