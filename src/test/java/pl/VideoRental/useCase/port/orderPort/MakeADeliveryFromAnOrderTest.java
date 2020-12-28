package pl.VideoRental.useCase.port.orderPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.deliveryPort.GetAllDeliveries;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MakeADeliveryFromAnOrderTest {

    @Autowired
    MakeADeliveryFromAnOrder makeADeliveryFromAnOrder;

    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;

    @Test
    void shouldMakeADelivery(){
        //given
        int firstIndexNumber = 0;
        User user = getAllUsers.getAll().get(firstIndexNumber);
        Copy copy = getAllCopies.getAll().get(firstIndexNumber);
        List<Copy> copies = new ArrayList<>();
        copies.add(copy);
        BigDecimal cost = BigDecimal.valueOf(15);
        Order order = Order.builder()
                .user(user)
                .copies(copies)
                .cost(cost)
                .build();
        //when
        Delivery delivery = makeADeliveryFromAnOrder.makeADelivery(order, user.getAddress());
        //then
        assertEquals(user.getAddress(), delivery.getAddress());
        assertEquals(user.getId(), delivery.getOrder().getUser().getId());
        assertEquals(copy.getId(), delivery.getOrder().getCopies().get(firstIndexNumber).getId());
        assertFalse(delivery.isDelivered());
    }


}