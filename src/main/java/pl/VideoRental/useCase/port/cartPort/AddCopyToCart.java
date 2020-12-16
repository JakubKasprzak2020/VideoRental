package pl.VideoRental.useCase.port.cartPort;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.port.copyPort.IsCopyFree;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AddCopyToCart {

    private final Cart cart;
    private final IsCopyFree isCopyFree;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;
    private final CopyRepository copyRepository;

    public void add(User user, Copy copy, int rentalDays, LocalDate rentalDate) throws CopyIsAlreadyRentedException {
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
