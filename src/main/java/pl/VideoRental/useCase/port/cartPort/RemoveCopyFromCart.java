package pl.VideoRental.useCase.port.cartPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.port.cartPort.CalculateCostOfCopiesInCart;

@Component
@RequiredArgsConstructor
public class RemoveCopyFromCart {

    private final Cart cart;
    private final CopyRepository copyRepository;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;

    public void removeCopy(User user, Copy copy){
        cart.getCopies().removeIf(c -> c.getId() == copy.getId());
     //   cart.getCopies().remove(copy);
        copy.setAvailable(true);
        copy.setRentalDays(0);
        copy.setRentalDate(null);
        copyRepository.save(copy);
        calculateCostOfCopiesInCart.calculate(user);
    }

}
