package pl.VideoRental.useCase.port.orderPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Delivery;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.exception.DeliveryDoesNotExistException;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.deliveryPort.CreateDeliveryFromAnOrder;
import pl.VideoRental.useCase.port.deliveryPort.GetDeliveryFromCatalog;
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
    @Autowired
    CreateDeliveryFromAnOrder createDeliveryFromAnOrder;
    @Autowired
    GetDeliveryFromCatalog getDeliveryFromCatalog;

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
        orderRepository.save(order);
        int ordersSizeBeforeDeleting = getAllOrders.getAll().size();
        long orderIdThatNotExist = 999;
        //when
        deleteOrder.deleteById(orderIdThatNotExist);
        int ordersSizeAfterDeleting = getAllOrders.getAll().size();
        //then
        assertEquals(ordersSizeBeforeDeleting, ordersSizeAfterDeleting);
        deleteOrder.deleteById(order.getId());
    }

    @Test
    void shouldDeleteDeliveryWhileDeletingOrder(){ //because of orphan removal in Order.class
        //given
        Order order = makeAnOrder();
        orderRepository.save(order);
        Delivery delivery = createDeliveryFromAnOrder.makeDelivery(order.getId(), order.getUser().getAddress());
        long deliveryId = delivery.getId();
        //when
        deleteOrder.deleteById(order.getId());
        //then
        assertThrows(DeliveryDoesNotExistException.class, ()-> getDeliveryFromCatalog.getById(deliveryId));
    }


}