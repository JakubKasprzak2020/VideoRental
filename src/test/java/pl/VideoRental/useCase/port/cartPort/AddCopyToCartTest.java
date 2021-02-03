package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.*;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AddCopyToCartTest {

    @Autowired
    AddCopyToCart addCopyToCart;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    Cart cart;
    @Autowired
    EmptyACart emptyACart;


    @Test
    void shouldAddCopyToCart() throws CopyIsAlreadyRentedException {
        //given
        User user = getAllUsers.getAll().get(0);
        Copy copy = getAllCopies.getAll().get(0);
        int rentalDays = 2;
        LocalDate rentalDate = LocalDate.of(2020, 1, 1);
        //when
        emptyACart.empty(cart);
        addCopyToCart.add(user, copy, rentalDays, rentalDate);
        //then
        Copy copyFromCart = cart.getCopies().get(0);
        assertEquals(copy.getId(), copyFromCart.getId());
        assertEquals(copy.getMovie().getTitle(), copyFromCart.getMovie().getTitle());
        assertFalse(copyFromCart.isAvailable());
        assertEquals(rentalDate, copyFromCart.getRentalDate());
        assertEquals(rentalDays, copyFromCart.getRentalDays());
        assertTrue(cart.getToPay().intValue() > 0);
        assertNull(copyFromCart.getUser()); //User is added to Copy during MakeAnOrderFromCartContent process
        emptyACart.empty(cart);
    }



    @Test
    void shouldThrowExceptionWhileAddingToCartCopyThatIsNotAvailable() {
        //given
        User user = getAllUsers.getAll().get(0);
        Copy copy = getAllCopies.getAll().get(0);
        int rentalDays = 2;
        LocalDate rentalDate = LocalDate.of(2020, 1, 1);
        //when
        copy.setAvailable(false);
        //then
        assertThrows(CopyIsAlreadyRentedException.class, ()->addCopyToCart.add(user, copy, rentalDays, rentalDate));
        copy.setAvailable(true);
    }


}