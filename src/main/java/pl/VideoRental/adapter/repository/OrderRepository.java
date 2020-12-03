package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.VideoRental.domain.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
}
