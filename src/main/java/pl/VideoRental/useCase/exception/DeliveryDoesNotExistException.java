package pl.VideoRental.useCase.exception;


public class DeliveryDoesNotExistException extends Exception {
    public DeliveryDoesNotExistException(Long id) {
        super(String.format("Delivery with id %s does not exist.", id));
    }
}
