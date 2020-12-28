package pl.VideoRental.useCase.port.orderPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.Order;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetAllOrdersTest {

    @Autowired
    private GetAllOrders getAllOrders;
    @Autowired
    private GetAllUsers getAllUsers;
    @Autowired
    private GetAllCopies getAllCopies;
    @Autowired
    OrderRepository orderRepository;

    @Test
    public void shouldGetAllOrders() {
        //given
        int firstIndexNumber = 0;
        User user = getAllUsers.getAll().get(firstIndexNumber);
        Copy copy1 = getAllCopies.getAll().get(firstIndexNumber);
        Copy copy2 = getAllCopies.getAll().get(firstIndexNumber + 1);
        List<Copy> copies = new ArrayList<>();
        copies.add(copy1);
        copies.add(copy2);
        BigDecimal cost = BigDecimal.TEN;
        //when
        Order order = Order.builder()
                .user(user)
                .copies(copies)
                .cost(cost)
                .build();
        orderRepository.save(order);
        List<Order> orders = getAllOrders.getAll();
        //then
        assertEquals(1, orders.size());
        assertEquals(user.getId(), orders.get(firstIndexNumber).getUser().getId());
        assertEquals(2, orders.get(firstIndexNumber).getCopies().size());
    }

}