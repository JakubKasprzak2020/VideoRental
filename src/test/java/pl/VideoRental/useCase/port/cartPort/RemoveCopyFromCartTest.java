package pl.VideoRental.useCase.port.cartPort;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.port.copyPort.GetAllCopies;
import pl.VideoRental.useCase.port.userPort.GetAllUsers;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RequiredArgsConstructor
class RemoveCopyFromCartTest {

    @Autowired
    RemoveCopyFromCart removeCopyFromCart;
    @Autowired
    GetAllCopies getAllCopies;
    @Autowired
    GetAllUsers getAllUsers;
    @Autowired
    EmptyACart emptyACart;
    @Autowired
    AddCopyToCart addCopyToCart;
    @Autowired
    Cart cart;

 @Test
    void shouldRemoveCopyFromCart() throws CopyIsAlreadyRentedException {
     //given
     User user = getAllUsers.getAll().get(0);
     Copy copy = getAllCopies.getAll().get(0);
     int rentalDays = 2;
     LocalDate rentalDate = LocalDate.of(2020, 1, 1);
     //when
     emptyACart.empty(cart);
     addCopyToCart.add(user, copy, rentalDays, rentalDate);
     removeCopyFromCart.removeCopy(user, copy);
     //then
     assertTrue(copy.isAvailable());
     assertNull(copy.getRentalDate());
     assertEquals(0, copy.getRentalDays());
     assertNull(copy.getUser());
     assertEquals(0, cart.getCopies().size());
     assertEquals(BigDecimal.ZERO, cart.getToPay());
 }



}