package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;
import pl.VideoRental.useCase.port.deliveryPort.Deliver;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeliverTest {


   @Autowired
    private Deliver deliver;
   @Autowired
   private DeleteDelivery deleteDelivery;
   @Autowired
   private DeliveryRepository deliveryRepository;
   @Autowired
   private GetDeliveryFromCatalog getDeliveryFromCatalog;

   @Test
    public void shouldDeliverDelivery(){
       //given
       Delivery delivery = new Delivery();
       //when
       deliver.deliverToUser(delivery);
       //then
       assertTrue(delivery.isDelivered());
       deleteDelivery.deleteById(delivery.getId());
   }

   @Test
   public void shouldDeliverWithDeliveryId() throws DeliveryDoesNotExistException {
      //given
      Delivery delivery = new Delivery();
      //when
      deliveryRepository.save(delivery);
      deliver.deliverToUser(delivery.getId());
      //then
      Delivery deliveredDelivery = getDeliveryFromCatalog.getById(delivery.getId());
      assertTrue(deliveredDelivery.isDelivered());
      deleteDelivery.deleteById(delivery.getId());
   }

}