package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyDoesNotExist;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRented;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AddCopyToCart {

    private final IsCopyFree isCopyFree;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;
    private final CopyRepository copyRepository;

    public void add(Cart cart, Copy copy, int rentalDays, LocalDate rentalDate) throws CopyIsAlreadyRented {
        if (isCopyFree.isFree(copy)) {
            cart.getCopies().add(copy);
            copy.setAvailable(false);
            copy.setRentalDate(rentalDate);
            copy.setRentalDays(rentalDays);
            copyRepository.save(copy);
            calculateCostOfCopiesInCart.calculate(cart);
        } else {
            throw new CopyIsAlreadyRented(copy.getId());
        }


    }


}
