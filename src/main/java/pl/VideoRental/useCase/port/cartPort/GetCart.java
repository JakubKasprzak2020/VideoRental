package pl.VideoRental.useCase.port.cartPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Cart;

@Component
@RequiredArgsConstructor
public class GetCart {

    private final Cart cart;

    public Cart get(){
        return cart;
    }
}
