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
class GetOrderFromCatalogTest {

    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    GetOrderFromCatalog getOrderFromCatalog;
    @Autowired
    DeleteOrder deleteOrder;

    private final int firstIndexNumber = 0;

    private Order makeAnOrder() {
        return Order.builder()
                .copies(getAllCopies.getAll())
                .user(getAllUsers.getAll().get(firstIndexNumber))
                .cost(BigDecimal.valueOf(20))
                .build();
    }


    @Test
    public void ShouldGetOrderFromCatalogById() throws OrderDoesNotExistException {
        //given
        Order order = makeAnOrder();
        //when
        orderRepository.save(order);
        Order orderFromCatalog = getOrderFromCatalog.getById(order.getId());
        //then
        assertEquals(order.getId(), orderFromCatalog.getId());
        assertEquals(order.getUser().getId(), orderFromCatalog.getUser().getId());
        assertEquals(order.getCopies().size(), orderFromCatalog.getCopies().size());
        assertEquals(order.getCopies().get(firstIndexNumber).getId(), orderFromCatalog.getCopies().get(firstIndexNumber).getId());
        deleteOrder.deleteById(order.getId());
    }


    @Test
    public void ShouldThrowExceptionWhenGettingOrderByIdThatNotExist() {
        //given
        long idOfOrderThatNotExist = 1300;
        //then
        assertThrows(OrderDoesNotExistException.class, ()-> getOrderFromCatalog.getById(idOfOrderThatNotExist));
    }


}