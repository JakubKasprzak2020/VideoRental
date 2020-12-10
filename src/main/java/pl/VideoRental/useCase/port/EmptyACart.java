package pl.VideoRental.useCase.port;

import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;

import java.math.BigDecimal;
import java.util.ArrayList;

@Component
public class EmptyACart {

    public void empty(Cart cart) {
        cart.setToPay(BigDecimal.ZERO);
        for (Copy copy : cart.getCopies()){
            copy.setAvailable(true);
        }
        cart.getCopies().clear();
    }

}
