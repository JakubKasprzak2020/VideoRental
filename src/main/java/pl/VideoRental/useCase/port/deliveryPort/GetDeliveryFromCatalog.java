package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

@Component
@RequiredArgsConstructor
public class GetDeliveryFromCatalog {

    private final DeliveryRepository deliveryRepository;

    public Delivery getById(long id) throws DeliveryDoesNotExistException {
        return deliveryRepository.findById(id).orElseThrow(()-> new DeliveryDoesNotExistException(id));
    }
}
