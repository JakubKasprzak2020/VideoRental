package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetDeliveryFromCatalogTest {


    @Autowired
    GetDeliveryFromCatalog getDeliveryFromCatalog;
    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    DeleteDelivery deleteDelivery;


    @Test
    void shouldGetDeliveryFromCatalog() throws DeliveryDoesNotExistException {
        //given
        Delivery delivery = new Delivery();
        deliveryRepository.save(delivery);
        //when
        Delivery deliveryFromCatalog = getDeliveryFromCatalog.getById(delivery.getId());
        //then
        assertEquals(delivery.getId(), deliveryFromCatalog.getId());
        deleteDelivery.deleteById(delivery.getId());
    }

    @Test
    void shouldThrowExceptionWhenGettingDeliveryByIdThatNotExist(){
        //given
        long idOfDeliveryThatDoesNotExist = 1000;
        //then
        assertThrows(DeliveryDoesNotExistException.class, ()-> getDeliveryFromCatalog.getById(idOfDeliveryThatDoesNotExist));
    }




}