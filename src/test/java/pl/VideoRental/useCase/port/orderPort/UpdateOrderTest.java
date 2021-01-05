package pl.VideoRental.useCase.port.orderPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.Order;
import pl.VideoRental.useCase.exception.OrderDoesNotExistException;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UpdateOrderTest {

    @Autowired
    UpdateOrder updateOrder;
    @Autowired
    GetOrderFromCatalog getOrderFromCatalog;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    DeleteOrder deleteOrder;

    int costOfOrder = 100;
    Order order = Order.builder()
            .cost(BigDecimal.valueOf(costOfOrder))
            .build();

    @Test
    void shouldUpdateOrder() throws OrderDoesNotExistException {
        //given
        orderRepository.save(order);
        int newCostOfOrder = 10;
        //when
        order.setCost(BigDecimal.valueOf(newCostOfOrder));
        updateOrder.update(order.getId(), order);
        //then
        Order updatedOrder = getOrderFromCatalog.getById(order.getId());
        assertEquals(newCostOfOrder, updatedOrder.getCost().intValue());
        deleteOrder.deleteById(order.getId());
    }


    @Test
    void shouldNotUpdateOrderWhenIdIsIncorrect() throws OrderDoesNotExistException {
        //given
        long idOfOrderThatDoesNotExist = 560;
        orderRepository.save(order);
        int newCostOfOrder = 10;
        //when
        order.setCost(BigDecimal.valueOf(newCostOfOrder));
        updateOrder.update(idOfOrderThatDoesNotExist, order);
        //then
        Order notUpdatedOrder = getOrderFromCatalog.getById(order.getId());
        assertNotEquals(BigDecimal.valueOf(newCostOfOrder), notUpdatedOrder.getCost());
        deleteOrder.deleteById(order.getId());
    }


}