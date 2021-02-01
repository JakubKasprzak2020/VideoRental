package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.adapter.repository.CopyRepository;
import pl.VideoRental.authentication.ApplicationUser;
import pl.VideoRental.authentication.ApplicationUserRepository;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;
import pl.VideoRental.useCase.port.cartPort.AddCopyToCart;
import pl.VideoRental.useCase.port.cartPort.EmptyACart;
import pl.VideoRental.useCase.port.cartPort.GetCart;
import pl.VideoRental.useCase.port.cartPort.RemoveCopyFromCart;
import pl.VideoRental.useCase.port.copyPort.GetCopyFromCatalog;
import pl.VideoRental.useCase.port.userPort.GetUserFromCatalog;

import java.security.Principal;
import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final AddCopyToCart addCopyToCart;
    private final EmptyACart emptyACart;
    private final RemoveCopyFromCart removeCopyFromCart;
    private final GetCart getCart;
    private final GetCopyFromCatalog getCopyFromCatalog;
    private final GetUserFromCatalog getUserFromCatalog;

    @GetMapping("/api/cart")
    @ResponseStatus(HttpStatus.OK)
    public Cart get(){
        return getCart.get();
    }

    //TODO - probably this method should be replaced by addMovieToCatalog - number of free copy should be chosen automatically
    @PutMapping("/api/cart/in/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void add(@AuthenticationPrincipal UserDetails userDetails,
                    @PathVariable long copyId,
                    @RequestBody String rentalDays) throws CopyDoesNotExistException, CopyIsAlreadyRentedException, UserDoesNotExistException {
        User user = getUserFromCatalog.getByEmail(userDetails.getUsername());
        Copy copy = getCopyFromCatalog.get(copyId);
        int rentalDaysInteger = Integer.parseInt(rentalDays);
        LocalDate rentalDate = LocalDate.now();
        addCopyToCart.add(user, copy, rentalDaysInteger, rentalDate);
    }

    @PutMapping("/api/cart/out/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@AuthenticationPrincipal UserDetails userDetails,
                       @PathVariable long copyId) throws UserDoesNotExistException, CopyDoesNotExistException {
        User user = getUserFromCatalog.getByEmail(userDetails.getUsername());
        Copy copy = getCopyFromCatalog.get(copyId);
        removeCopyFromCart.removeCopy(user, copy);
    }

    @PatchMapping("/api/cart")
    @ResponseStatus(HttpStatus.OK)
    public void empty(){
        emptyACart.empty();
    }
}
