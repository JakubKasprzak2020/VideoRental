package pl.VideoRental.adapter.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.VideoRental.domain.Cart;
import pl.VideoRental.useCase.port.cartPort.AddCopyToCart;
import pl.VideoRental.useCase.port.cartPort.EmptyACart;
import pl.VideoRental.useCase.port.cartPort.GetCart;
import pl.VideoRental.useCase.port.cartPort.RemoveCopyFromCart;

@RestController
@RequiredArgsConstructor
public class CartController {

    private final AddCopyToCart addCopyToCart;
    private final EmptyACart emptyACart;
    private final RemoveCopyFromCart removeCopyFromCart;
    private final GetCart getCart;

    @GetMapping("/api/cart")
    @ResponseStatus(HttpStatus.OK)
    public Cart get(){
        return getCart.get();
    }

    //TODO Spring Security First
    @PutMapping("/api/cart/in/{copyId}")
    @ResponseStatus(HttpStatus.OK)
    public void add(@PathVariable long copyId){
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
