package pl.VideoRental.adapter.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pl.VideoRental.domain.Delivery;

@Repository
public interface DeliveryRepository extends CrudRepository<Delivery, Long> {
}
