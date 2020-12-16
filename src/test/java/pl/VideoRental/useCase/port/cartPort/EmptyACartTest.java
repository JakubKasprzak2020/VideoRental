package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmptyACartTest {


    @Autowired
    private EmptyACart emptyACart;

    @Test
    public void shouldEmptyACart(){
        //given
        Copy copy1 = Copy.builder().isAvailable(false).build();
        Copy copy2 = Copy.builder().isAvailable(false).build();
        List<Copy> copies = new ArrayList<>();
        copies.add(copy1);
        copies.add(copy2);
        Cart cart = Cart.builder()
                .copies(copies)
                .toPay(BigDecimal.TEN)
                .build();
        //when
        emptyACart.empty(cart);
        //then
        assertTrue(copy1.isAvailable());
        assertTrue(copy2.isAvailable());
        assertEquals(BigDecimal.ZERO, cart.getToPay());
        assertTrue(cart.getCopies().isEmpty());
    }

}