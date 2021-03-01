package pl.VideoRental.useCase.port.deliveryPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import pl.VideoRental.adapter.repository.OrderRepository;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.orderPort.DeleteOrder;
import pl.VideoRental.useCase.port.userPort.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class CreateDeliveryFromAnOrderTest {

    @Autowired
    CreateDeliveryFromAnOrder createDeliveryFromAnOrder;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    DeleteOrder deleteOrder;
    @Autowired
    DeleteDelivery deleteDelivery;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    CreateUser createUser;
    @Autowired
    DeleteUser deleteUser;
    @Autowired
    GetUserFromCatalog getUserFromCatalog;

    private UserSignInData newUserSignInData = UserSignInData.builder()
            .address("Elm street")
            .email("freddy@elmstreet.com")
            .lastName("Krueger")
            .name("Freddy")
            .password("sweet dreams")
            .build();

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
        Delivery delivery = createDeliveryFromAnOrder.makeDelivery(order, user.getAddress());
        //then
        assertEquals(user.getAddress(), delivery.getAddress());
        assertEquals(user.getId(), delivery.getOrder().getUser().getId());
        assertEquals(copy.getId(), delivery.getOrder().getCopies().get(firstIndexNumber).getId());
        assertFalse(delivery.isDelivered());
        deleteOrder.deleteById(order.getId());
        deleteDelivery.deleteById(delivery.getId());
    }


    @Test
    void shouldMakeADeliveryFromOrderId(){
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
        orderRepository.save(order);
        //when
        Delivery delivery = createDeliveryFromAnOrder.makeDelivery(order.getId(), user.getAddress());
        //then
        assertEquals(user.getAddress(), delivery.getAddress());
        assertEquals(user.getId(), delivery.getOrder().getUser().getId());
        assertEquals(copy.getId(), delivery.getOrder().getCopies().get(firstIndexNumber).getId());
        assertFalse(delivery.isDelivered());
        deleteOrder.deleteById(order.getId());
        deleteDelivery.deleteById(delivery.getId());
    }

    @Test
    void shouldPromoteUserType() throws UserDoesNotExistException {
        User user = createUser.create(newUserSignInData);
        Copy copy = getAllCopies.getAll().get(0);
        List<Copy> copies = new ArrayList<>();
        copies.add(copy);
        BigDecimal cost = BigDecimal.valueOf(PromoteUserType.AMOUNT_FOR_SILVER_USER_TYPE);
        Order order = Order.builder()
                .user(user)
                .copies(copies)
                .cost(cost)
                .build();
        orderRepository.save(order);
        //when
        Delivery delivery = createDeliveryFromAnOrder.makeDelivery(order.getId(), user.getAddress());
        //then
        User savedUser = getUserFromCatalog.getById(user.getId());
        assertEquals(UserType.SILVER, savedUser.getUserType());

        deleteOrder.deleteById(order.getId());
        deleteDelivery.deleteById(delivery.getId());
        deleteUser.deleteById(user.getId());
    }


}