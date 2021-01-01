package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.port.orderPort.DeleteOrder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllDeliveriesTest {

    @Autowired
    DeliveryRepository deliveryRepository;
    @Autowired
    GetAllDeliveries getAllDeliveries;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DeleteDelivery deleteDelivery;
    @Autowired
    DeleteOrder deleteOrder;

    @Test
    void shouldGetAllDeliveries() {
        //given
        Order order = new Order();
        Delivery delivery = Delivery.builder()
                .order(order)
                .address("Banana street 2005")
                .build();
        orderRepository.save(order);
        deliveryRepository.save(delivery);
        //when
        List<Delivery> deliveries = getAllDeliveries.getAll();
        //then
        assertNotNull(deliveries);
        assertEquals(1, deliveries.size());
        deleteOrder.deleteById(order.getId());
        deleteDelivery.deleteById(delivery.getId());
    }

}