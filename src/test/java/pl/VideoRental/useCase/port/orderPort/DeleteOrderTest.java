package pl.VideoRental.useCase.port.orderPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DeleteOrderTest {

    @Autowired
    DeleteOrder deleteOrder;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    GetOrderFromCatalog getOrderFromCatalog;
    @Autowired
    GetAllOrders getAllOrders;


    private Order makeAnOrder() {
        return Order.builder()
                .copies(getAllCopies.getAll())
                .user(getAllUsers.getAll().get(0))
                .cost(BigDecimal.valueOf(20))
                .build();
    }

    @Test
    void shouldDeleteOrderById() {
        //given
        Order order = makeAnOrder();
        orderRepository.save(order);
        //when
        deleteOrder.deleteById(order.getId());
        //then
        assertThrows(OrderDoesNotExistException.class, () -> getOrderFromCatalog.getById(order.getId()));
    }

    @Test
    void shouldNotDeleteAnythingWithWrongIdAsAnArgument() {
        //given
        Order order = makeAnOrder();
        orderRepository.save(order); //adding order to catalog because there are no orders in sample data initialization
        int ordersSizeBeforeDeleting = getAllOrders.getAll().size();
        long orderIdThatNotExist = 999;
        //when
        deleteOrder.deleteById(orderIdThatNotExist);
        int ordersSizeAfterDeleting = getAllOrders.getAll().size();
        //then
        assertEquals(ordersSizeBeforeDeleting, ordersSizeAfterDeleting);
        deleteOrder.deleteById(order.getId());
    }

    //TODO prove that after deleting an order there is also no delivery


}