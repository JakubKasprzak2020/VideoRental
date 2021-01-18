package pl.VideoRental.useCase.port.cartPort;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GetCartTest {

    @Autowired
    private GetCart getCart;


    @Test
    void shouldGetCart(){
        //given
        BigDecimal cost = BigDecimal.TEN;
        Copy copy = new Copy();
        //when
        Cart cart = getCart.get();
        cart.setToPay(cost);
        cart.setCopies(Collections.singletonList(copy));
        //then
        assertNotNull(cart);
        assertEquals(cost, cart.getToPay());
        assertEquals(1, cart.getCopies().size());
        cart.setToPay(BigDecimal.ZERO);
        cart.setCopies(new ArrayList<>());
    }

}