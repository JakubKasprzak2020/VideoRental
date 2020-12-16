package pl.VideoRental.useCase.port.deliveryPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllDeliveries {

    private final DeliveryRepository deliveryRepository;

    public List<Delivery> getAll(){
        List <Delivery> deliveries = new ArrayList<>();
        deliveryRepository.findAll().iterator().forEachRemaining(deliveries::add);
        return deliveries;
    }


}
