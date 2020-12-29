package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

@Component
@RequiredArgsConstructor
public class DeleteDelivery {

    private final DeliveryRepository deliveryRepository;
    private final GetDeliveryFromCatalog getDeliveryFromCatalog;

    public void deleteById(long id) {
        try {
            getDeliveryFromCatalog.getById(id);
            deliveryRepository.deleteById(id);
        } catch (DeliveryDoesNotExistException exception){
            System.out.println(exception.getMessage());
        }
    }
}
