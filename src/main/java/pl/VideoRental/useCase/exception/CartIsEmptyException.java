package pl.VideoRental.useCase.exception;

public class CartIsEmptyException extends Exception {

    public CartIsEmptyException() {
        super("There is no copy in the cart.");
    }
}
