package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;

@Component
@RequiredArgsConstructor
public class Deliver {

    private final DeliveryRepository deliveryRepository;


    public void deliverToUser(Delivery delivery) {
        delivery.setDelivered(true);
        deliveryRepository.save(delivery);
    }
}
