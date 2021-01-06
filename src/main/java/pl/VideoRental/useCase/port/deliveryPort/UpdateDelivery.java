package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

@Component
@RequiredArgsConstructor
public class UpdateDelivery {

    private final DeliveryRepository deliveryRepository;
    private final GetDeliveryFromCatalog getDeliveryFromCatalog;

    /**
     * The only thing that could be changed by this method is an address of delivery and isDelivered field.
     */

    public void update(long id, Delivery newDelivery){
        try {
           Delivery oldDelivery = getDeliveryFromCatalog.getById(id);
           oldDelivery.setAddress(newDelivery.getAddress());
           oldDelivery.setDelivered(newDelivery.isDelivered());
           deliveryRepository.save(oldDelivery);
        } catch (DeliveryDoesNotExistException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
