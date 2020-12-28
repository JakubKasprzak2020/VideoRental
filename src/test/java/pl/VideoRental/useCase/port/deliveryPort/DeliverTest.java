package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.useCase.port.deliveryPort.Deliver;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeliverTest {


   @Autowired
    private Deliver deliver;

   @Test
    public void shouldDeliverDelivery(){
       //given
       Delivery delivery = new Delivery();
       //when
       deliver.deliverToUser(delivery);
       //then
       assertTrue(delivery.isDelivered());
   }

}