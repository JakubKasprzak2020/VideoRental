package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteDeliveryTest {


    @Autowired
    DeleteDelivery deleteDelivery;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    GetDeliveryFromCatalog getDeliveryFromCatalog;
    @Autowired
    GetAllDeliveries getAllDeliveries;

    @Test
    void shouldDeleteDelivery(){
        //given
        Delivery delivery = new Delivery();
        //when
        deliveryRepository.save(delivery);
        deleteDelivery.deleteById(delivery.getId());
        //then
        assertThrows(DeliveryDoesNotExistException.class, ()-> getDeliveryFromCatalog.getById(delivery.getId()));
    }


    @Test
    void shouldNotDeleteAnythingWithWrongIdAsAnArgument(){
        //given
        Delivery delivery = new Delivery();
        deliveryRepository.save(delivery); // because there is no any delivery in sample data init
        long idOfDeliveryThatDoesNotExist = 876;
        int sizeOfDeliveriesBeforeDeleting = getAllDeliveries.getAll().size();
        //when
        deleteDelivery.deleteById(idOfDeliveryThatDoesNotExist);
        int sizeOfDeliveriesAfterDeleting = getAllDeliveries.getAll().size();
        //then
        assertEquals(sizeOfDeliveriesBeforeDeleting, sizeOfDeliveriesAfterDeleting);
        deleteDelivery.deleteById(delivery.getId());
    }

}