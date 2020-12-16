package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.cartPort.CalculateCostOfCopiesInCart;

@Component
@RequiredArgsConstructor
public class RemoveACopyFromACart {

    private final CopyRepository copyRepository;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;

    public void removeCopy(User user, Copy copy){
        Cart cart = user.getCart();
        copy.setAvailable(true);
        copyRepository.save(copy);
        cart.getCopies().remove(copy);
        calculateCostOfCopiesInCart.calculate(user);
    }

}
