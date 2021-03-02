package pl.VideoRental.adapter.controller;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.domain.Copy;
import pl.VideoRental.domain.User;
import pl.VideoRental.useCase.exception.CopyDoesNotExistException;
import pl.VideoRental.useCase.exception.CopyIsAlreadyRentedException;
import pl.VideoRental.useCase.exception.MovieIsNotAvailableException;
import pl.VideoRental.useCase.exception.UserDoesNotExistException;
import pl.VideoRental.useCase.port.cartPort.*;
import pl.VideoRental.useCase.port.copyPort.GetCopyFromCatalog;
import pl.VideoRental.useCase.port.userPort.GetUserFromCatalog;

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
    private final AddMovieToCart addMovieToCart;

    @GetMapping("/api/cart")
    @ResponseStatus(HttpStatus.OK)
    @JsonProperty //without this annotation there is InvalidDefinitionException while trying to get cart by Postman
    public Cart get(){
        return getCart.get();
    }

    //TODO - probably this method is redundant
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

    @PutMapping("api/cart/{movieId}")
    @ResponseStatus(HttpStatus.OK)
    public void addMovie(@AuthenticationPrincipal UserDetails userDetails,
                         @PathVariable long movieId,
                         @RequestBody String rentalDays) throws UserDoesNotExistException, MovieIsNotAvailableException, CopyIsAlreadyRentedException {
        User user = getUserFromCatalog.getByEmail(userDetails.getUsername());
        int rentalDaysInteger = Integer.parseInt(rentalDays);
        addMovieToCart.add(user, movieId, rentalDaysInteger);
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
