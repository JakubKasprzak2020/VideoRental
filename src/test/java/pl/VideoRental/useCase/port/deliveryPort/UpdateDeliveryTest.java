package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateDeliveryTest {

    @Autowired
    UpdateDelivery updateDelivery;
    @Autowired
    GetDeliveryFromCatalog getDeliveryFromCatalog;
    @Autowired
    DeleteDelivery deleteDelivery;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    GetAllDeliveries getAllDeliveries;

    private final Delivery oldDelivery = Delivery.builder()
            .address("New York")
            .isDelivered(true)
            .build();

    private final Delivery newDelivery = Delivery.builder()
            .address("London")
            .isDelivered(false)
            .build();

    @Test
    void shouldUpdateDelivery() throws DeliveryDoesNotExistException {
        //given
        deliveryRepository.save(oldDelivery);
        long oldDeliveryId = oldDelivery.getId();
        //when
        updateDelivery.update(oldDeliveryId, newDelivery);
        //then
        Delivery updatedDelivery = getDeliveryFromCatalog.getById(oldDeliveryId);
        assertEquals(newDelivery.isDelivered(), updatedDelivery.isDelivered());
        assertEquals(newDelivery.getAddress(), updatedDelivery.getAddress());
        deleteDelivery.deleteById(oldDeliveryId);
    }


    @Test
    void shouldNotUpdateDeliveryWhenIdIsIncorrect() {
        //given
        deliveryRepository.save(oldDelivery);
        long idOfDeliveryThatDoesNotExist = 8050;
        //when
        updateDelivery.update(idOfDeliveryThatDoesNotExist, newDelivery);
        //then
       List<Delivery> deliveriesToLondon = getAllDeliveries.getAll().stream().filter(delivery -> delivery
                .getAddress().equals(newDelivery.getAddress()))
                .collect(Collectors.toList());
       assertTrue(deliveriesToLondon.isEmpty());
       deleteDelivery.deleteById(oldDelivery.getId());
    }


}