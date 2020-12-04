package pl.VideoRental.useCase.port;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRented;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AddCopyToCart {

    private final IsCopyFree isCopyFree;
    private final CalculateCostOfCopiesInCart calculateCostOfCopiesInCart;

    public void add(Copy copy, Cart cart, int days) throws CopyIsAlreadyRented {
        if (isCopyFree.isFree(copy)) {
            copy.setRentDate(LocalDate.now());
            copy.setRentalDays(days);
            cart.getCopies().add(copy);
            calculateCostOfCopiesInCart.calculate(cart);
        } else {
            throw new CopyIsAlreadyRented(copy.getId());
        }


    }


}
