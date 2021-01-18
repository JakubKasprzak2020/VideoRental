package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

@Component
@RequiredArgsConstructor
public class Deliver {

    private final DeliveryRepository deliveryRepository;
    private final GetDeliveryFromCatalog getDeliveryFromCatalog;


    public void deliverToUser(Delivery delivery) {
        delivery.setDelivered(true);
        deliveryRepository.save(delivery);
    }

    public void deliverToUser(long deliveryId){
        try {
            Delivery delivery = getDeliveryFromCatalog.getById(deliveryId);
            deliverToUser(delivery);
        } catch (DeliveryDoesNotExistException exception){
            System.out.println(exception.getMessage());
        }
    }
}
