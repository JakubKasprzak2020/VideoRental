package pl.VideoRental.useCase.port.copyPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.port.cartPort.CalculateCostOfCopiesInCart;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AddCopyToCart {

    private final IsCopyFree isCopyFree;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;
    private final CopyRepository copyRepository;

    public void add(User user, Copy copy, int rentalDays, LocalDate rentalDate) throws CopyIsAlreadyRentedException {
        Cart cart = user.getCart();
        if (isCopyFree.isFree(copy)) {
            cart.getCopies().add(copy);
            copy.setAvailable(false);
            copy.setRentalDate(rentalDate);
            copy.setRentalDays(rentalDays);
            copyRepository.save(copy);
            calculateCostOfCopiesInCart.calculate(user);
        } else {
            throw new CopyIsAlreadyRentedException(copy.getId());
        }


    }


}
