package pl.VideoRental.useCase.exception;

public class OrderDoesNotExistException extends Exception {

    public OrderDoesNotExistException(long id) {
        super(String.format("Order with id %s does not exist.", id));
    }
}
