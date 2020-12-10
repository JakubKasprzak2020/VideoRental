package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;

@Component
@RequiredArgsConstructor
public class RemoveACopyFromACart {

    private final CopyRepository copyRepository;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;

    public void removeCopy(Cart cart, Copy copy){
        copy.setAvailable(true);
        copyRepository.save(copy);
        cart.getCopies().remove(copy);
        calculateCostOfCopiesInCart.calculate(cart);
    }

}
