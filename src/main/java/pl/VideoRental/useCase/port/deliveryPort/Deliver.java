package pl.VideoRental.useCase.port.deliveryPort;

import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Delivery;

@Component
public class Deliver {

    public void deliverToUser(Delivery delivery) {
        delivery.setDelivered(true);
    }
}
