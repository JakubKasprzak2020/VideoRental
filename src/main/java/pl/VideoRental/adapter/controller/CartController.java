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
    private final ApplicationUserRepository applicationUserRepository;

    @GetMapping("/api/cart")
    @ResponseStatus(HttpStatus.OK)
    public Cart get(){
        return getCart.get();
    }

    //TODO Spring Security First
    @PutMapping("/api/cart/in/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void add(@AuthenticationPrincipal UserDetails userDetails,
                    @PathVariable long copyId,
                    @RequestBody String rentalDays) throws CopyDoesNotExistException, CopyIsAlreadyRentedException {

        ApplicationUser applicationUser = applicationUserRepository.findByUsername(userDetails.getUsername());
        User user = getUserFromCatalog.getByApplicationUser(applicationUser);
        Copy copy = getCopyFromCatalog.get(copyId);
        int rentalDaysInteger = Integer.parseInt(rentalDays);
        LocalDate rentalDate = LocalDate.now();
        addCopyToCart.add(user, copy, rentalDaysInteger, rentalDate);
    }

    //TODO Spring Security First
    @PutMapping("/api/cart/out/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void remove(@PathVariable long copyId){
    }

    @PatchMapping("/api/cart")
    @ResponseStatus(HttpStatus.OK)
    public void empty(){
        emptyACart.empty();
    }
}
