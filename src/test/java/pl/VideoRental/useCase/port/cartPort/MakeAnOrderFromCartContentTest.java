package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.exception.*;
import pl.VideoRental.useCase.port.copyPort.CreateCopyOfAMovie;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.copyPort.ReturnACopy;
import pl.VideoRental.useCase.port.orderPort.DeleteOrder;
import pl.VideoRental.useCase.port.orderPort.GetAllOrders;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;
import pl.VideoRental.useCase.port.moviePort.CreateMovie;
import pl.VideoRental.useCase.port.moviePort.GetMovieFromCatalog;
import pl.VideoRental.useCase.port.userPort.CreateUser;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MakeAnOrderFromCartContentTest {

    @Autowired
    private GetAllUsers getAllUsers;
    @Autowired
    private GetAllCopies getAllCopies;
    @Autowired
    private AddCopyToCart addCopyToCart;
    @Autowired
    private MakeAnOrderFromCartContent makeAnOrderFromCartContent;
    @Autowired
    private EmptyACart emptyACart;
    @Autowired
    private Cart cart;
    @Autowired
    private ReturnACopy returnACopy;
    @Autowired
    DeleteOrder deleteOrder;


    @Test
    public void shouldMakeAnOrder() throws CopyIsAlreadyRentedException, CartIsEmptyException, CopyIsNotRentedException {
        //given
        int firstIndexNumber = 0;
        User user = getAllUsers.getAll().get(firstIndexNumber);
        Copy copy = getAllCopies.getAll().get(firstIndexNumber);
        int rentalDays = 1;
        LocalDate rentalDate = LocalDate.of(2020, 1, 1);
        addCopyToCart.add(user, copy, rentalDays, rentalDate);
        //when
        Order order = makeAnOrderFromCartContent.makeAnOrder(user);
        //then
        assertEquals(user.getName(), order.getUser().getName());
        assertEquals(1, order.getCopies().size());
        assertEquals(copy.getMovie().getTitle(), order.getCopies().get(firstIndexNumber).getMovie().getTitle());
        assertNull(order.getDelivery());
        assertTrue(order.getCost().intValue() > 0);
        returnACopy.returnACopy(copy);
        deleteOrder.deleteById(order.getId());
    }

    @Test
    public void shouldThrowAnExceptionWhenTheCartIsEmpty(){
        //given
        int firstIndexNumber = 0;
        User user = getAllUsers.getAll().get(firstIndexNumber);
        //when
        emptyACart.empty(cart);
        //then
        assertThrows(CartIsEmptyException.class, ()-> makeAnOrderFromCartContent.makeAnOrder(user));
    }









}