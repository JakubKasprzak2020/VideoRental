package pl.VideoRental.useCase.port;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.DeliveryRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.getAllUtils.GetAllDeliveries;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MakeADeliveryFromAnOrderTest {

    @Autowired
    MakeADeliveryFromAnOrder makeADeliveryFromAnOrder;

    @Autowired
    GetAllDeliveries getAllDeliveries;

    @Test
    void shouldMakeADelivery(){
        //given
        String address = "Raven Street 8/12 Warsaw";
        String name = "John";
        User user = new User();
        user.setName(name);
        user.setAddress(address);
        Order order = Order.builder().user(user).build();
        //when
        makeADeliveryFromAnOrder.makeADelivery(order, address);
        List<Delivery> deliveries = getAllDeliveries.getAll();
        Delivery delivery = deliveries.get(deliveries.size()-1);
        //then
        assertEquals(address, delivery.getAddress());
        assertEquals(name, delivery.getOrder().getUser().getName());

    }


}